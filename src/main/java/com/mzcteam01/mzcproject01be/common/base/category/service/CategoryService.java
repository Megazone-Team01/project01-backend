package com.mzcteam01.mzcproject01be.common.base.category.service;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                    () -> new CustomException("해당 상위 카테고리가 존재하지 않습니다")
            );
            builder.parent( parent );
        }
        categoryRepository.findByCode( code ).orElseThrow(
                () -> new CustomException("해당하는 카테고리 코드가 이미 존재합니다" )
        );
        builder.code( code );
        builder.name( name ).description( description );
        categoryRepository.save( builder.build() );
    }

    public void update( int categoryId, String name, String description ){
        Category category = categoryRepository.findById( categoryId ).orElseThrow(
                () -> new CustomException("해당하는 카테고리가 존재하지 않습니다")
        );
        category.update( name, description );
    }

    public void delete( int categoryId, int deletedBy ){
        Category category = categoryRepository.findById( categoryId ).orElseThrow(
                () -> new CustomException("해당하는 카테고리가 존재하지 않습니다")
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
}
