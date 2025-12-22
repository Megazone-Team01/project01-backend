package com.mzcteam01.mzcproject01be.common.base.category.service;

import com.mzcteam01.mzcproject01be.common.base.category.dto.request.CreateCategoryRequest;
import com.mzcteam01.mzcproject01be.common.base.category.dto.response.CategoryResponse;
import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.exception.CategoryErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import com.mzcteam01.mzcproject01be.common.utils.RelatedEntityChecker;
import com.mzcteam01.mzcproject01be.domains.lecture.entity.Lecture;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OfflineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.repository.OnlineLectureRepository;
import com.mzcteam01.mzcproject01be.domains.lecture.service.LectureFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RelatedEntityChecker relatedEntityChecker;
    private final OnlineLectureRepository onlineLectureRepository;
    private final OfflineLectureRepository offlineLectureRepository;

    @Transactional
    public void create(CreateCategoryRequest request, int createdBy ){
        Category.CategoryBuilder builder = Category.builder();
        if( request.getParentId() != null ){
            Category parent = categoryRepository.findById( request.getParentId() ).orElseThrow(
                    () -> new CustomException(CategoryErrorCode.CATEGORY_IS_ROOT.getMessage())
            );
            builder.parent( parent );
        }
        Category existed = categoryRepository.findByCode( request.getCode() ).orElse( null );
        if( existed != null ) throw new CustomException( CategoryErrorCode.CATEGORY_CODE_ALREADY_EXIST.getMessage());

        builder.code( request.getCode() );
        builder.name( request.getName() ).description( request.getDescription() )
                .createdBy( createdBy );
        categoryRepository.save( builder.build() );
    }

    @Transactional
    public void update( int categoryId, String name, String description ){
        Category category = categoryRepository.findById( categoryId ).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        category.update( name, description );
    }

    // 상하위 관계 없이 모두 조회
    public Map<String, String> findAll(){
        List<Category> categories = categoryRepository.findAll();
        Map<String, String> result = new HashMap<>();
        for( Category category : categories ) result.put( category.getCode(), category.getName() );
        return result;
    }

    // 계층 별 조회
    public Map<String, String> findByLayer( int parent ){
        // parent == 0이면 최상위 카테고리들
        List<Category> categories;
        Map<String, String> result = new HashMap<>();
        if( parent == 0 )  categories = categoryRepository.findAllByParentIsNullAndDeletedAtIsNull();
        else categories = categoryRepository.findAllByParentIdAndDeletedAtIsNull( parent );
        for( Category category : categories ) result.put( category.getCode(), category.getName() );
        return result;
    }

    // Category Code -> Category Layer
    public List<Category> categoryCodeToLayer( String categoryCode ){
        String[] categoryLayer = categoryCode.split("");
        List<Category> result = new ArrayList<>();
        for( String layer : categoryLayer ){
            Category categoryName = categoryRepository.findByCode( layer ).orElseThrow( () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage()) );
            result.add( categoryName );
        }
        return result;
    }


    public List<CategoryResponse> findChildCategory( Integer parentId ){
        if( parentId == null ) return categoryRepository.findAllByParentIsNullAndDeletedAtIsNull().stream()
                .map( CategoryResponse::of ).toList();
        else return categoryRepository.findAllByParentIdAndDeletedAtIsNull( parentId ).stream()
                .map( CategoryResponse::of ).toList();
    }

    @Transactional
    public void delete( int id, int deletedBy ){
        Category category = categoryRepository.findById( id ).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        // Lecture에 존재하는 모든 카테고리를 해당 카테고리의 상위 카테고리로 변경
        Category newCategory = category.getParent();
        String categoryCode = category.getCode();
        List<Lecture> allLectures = new ArrayList<>();
        allLectures.addAll( onlineLectureRepository.findAll() );
        allLectures.addAll( offlineLectureRepository.findAll() );
        for( Lecture lecture : allLectures ){
            if( lecture.getCategory().contains( categoryCode ) ){
                int pos = lecture.getCategory().indexOf( categoryCode );
                if( newCategory == null || pos == 0 ){
                    lecture.updateCategory( "0" );
                }
                else lecture.updateCategory( lecture.getCategory().substring(0, pos) );
            }
        }
        category.delete( deletedBy );
    }

    // ========== 새로 추가: 트리 구조 조회 ==========

    /**
     * 전체 카테고리 트리 조회 (삭제되지 않은 것만)
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryTree() {
        // 1. 모든 카테고리 조회 (삭제되지 않은 것만)
        List<Category> allCategories = categoryRepository.findAll().stream()
                .filter(c -> c.getDeletedAt() == null)
                .toList();

        // 2. Map으로 변환
        Map<Integer, CategoryResponse> categoryMap = new HashMap<>();

        for (Category category : allCategories) {
            CategoryResponse dto = CategoryResponse.builder()
                    .id(category.getId())
                    .code(category.getCode())
                    .name(category.getName())
                    .children(new ArrayList<>())
                    .build();
            categoryMap.put(category.getId(), dto);
        }

        // 3. 부모-자식 관계 구성
        List<CategoryResponse> rootCategories = new ArrayList<>();

        for (Category category : allCategories) {
            CategoryResponse dto = categoryMap.get(category.getId());

            if (category.getParent() == null) {
                // 루트 카테고리
                rootCategories.add(dto);
            } else {
                // 자식 카테고리 - 부모에게 추가
                Integer parentId = category.getParent().getId();
                CategoryResponse parent = categoryMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        return rootCategories;
    }

    /**
     * 1단계: Controller에서 이 메서드 호출
     */
    @Transactional(readOnly = true)
    public List<CategoryResponse> getLectureCategoryTree() {
        // 2단계로 이동
        CategoryResponse lecTree = getCategoryTreeByType("LEC");
        System.out.println(lecTree);
        return lecTree.getChildren();
    }

    /**
     * 2단계: 특정 타입의 카테고리 트리 조회
     */
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryTreeByType(String rootCode) {
        // 3단계: Repository 사용! ← 여기서 DB 조회
        List<Category> allCategories = categoryRepository.findAllNotDeleted();

        Category rootCategory = categoryRepository.findByCodeAndNotDeleted(rootCode)
                .orElseThrow(() -> new CustomException(
                        CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage()
                ));

        // 4단계: 트리 구성
        return buildCategoryTree(rootCategory, allCategories);
    }

    /**
     * 4단계: 트리 구조 생성
     */
    private CategoryResponse buildCategoryTree(Category root, List<Category> allCategories) {
        Map<Integer, CategoryResponse> categoryMap = new HashMap<>();

        for (Category category : allCategories) {
            CategoryResponse dto = CategoryResponse.builder()
                    .id(category.getId())
                    .code(category.getCode())
                    .name(category.getName())
                    .children(new ArrayList<>())
                    .build();
            categoryMap.put(category.getId(), dto);
        }

        for (Category category : allCategories) {
            if (category.getParent() != null) {
                Integer parentId = category.getParent().getId();
                CategoryResponse parent = categoryMap.get(parentId);
                CategoryResponse child = categoryMap.get(category.getId());

                if (parent != null && child != null) {
                    parent.getChildren().add(child);
                }
            }
        }

        return categoryMap.get(root.getId());
    }

}
