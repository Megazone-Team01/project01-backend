package com.mzcteam01.mzcproject01be.common.base.category.service;

import com.mzcteam01.mzcproject01be.common.base.category.dto.request.CreateCategoryRequest;
import com.mzcteam01.mzcproject01be.common.base.category.dto.response.CategoryResponse;
import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.exception.CategoryErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
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

    @Transactional
    public void create(CreateCategoryRequest request ){
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
                .createdBy(1);
        categoryRepository.save( builder.build() );
    }

    @Transactional
    public void update( int categoryId, String name, String description ){
        Category category = categoryRepository.findById( categoryId ).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        category.update( name, description );
    }

    @Transactional
    public void delete( int categoryId, int deletedBy ){
        Category category = categoryRepository.findById( categoryId ).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        category.delete( deletedBy );
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
        if( parent == 0 )  categories = categoryRepository.findAllByParentIsNull();
        else categories = categoryRepository.findAllByParentId( parent );
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
        if( parentId == null ) return categoryRepository.findAllByParentIsNull().stream()
                .map( CategoryResponse::of ).toList();
        else return categoryRepository.findAllByParentId( parentId ).stream()
                .map( CategoryResponse::of ).toList();
    }

}
