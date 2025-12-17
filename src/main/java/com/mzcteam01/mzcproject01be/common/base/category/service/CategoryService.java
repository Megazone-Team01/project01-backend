package com.mzcteam01.mzcproject01be.common.base.category.service;

import com.mzcteam01.mzcproject01be.common.base.category.dto.response.CategoryResponse;
import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.exception.CategoryErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void create( Integer parentId, String code, String name, String description ){
        Category.CategoryBuilder builder = Category.builder();
        if( parentId != null ){
            Category parent = categoryRepository.findById( parentId ).orElseThrow(
                    () -> new CustomException(CategoryErrorCode.CATEGORY_IS_ROOT.getMessage())
            );
            builder.parent( parent );
        }
        categoryRepository.findByCode( code ).orElseThrow(
                () -> new CustomException( CategoryErrorCode.CATEGORY_CODE_ALREADY_EXIST.getMessage() )
        );
        builder.code( code );
        builder.name( name ).description( description );
        categoryRepository.save( builder.build() );
    }

    public void update( int categoryId, String name, String description ){
        Category category = categoryRepository.findById( categoryId ).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        category.update( name, description );
    }

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
