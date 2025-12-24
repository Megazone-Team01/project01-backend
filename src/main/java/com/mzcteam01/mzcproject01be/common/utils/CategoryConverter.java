package com.mzcteam01.mzcproject01be.common.utils;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import com.mzcteam01.mzcproject01be.common.base.category.repository.CategoryRepository;
import com.mzcteam01.mzcproject01be.common.exception.CategoryErrorCode;
import com.mzcteam01.mzcproject01be.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final CategoryRepository categoryRepository;

    // Full Code String -> Layer
    // - ex. TDSF -> [ '프로그래밍', 'Java', 'Spring Boot', 'JPA' ]
    public List<String> fullCodeToLayer( String fullString ){
        List<String> layers = new ArrayList<>();
        String[] codes = fullString.split("");
        for( String code : codes ){
            Category category = categoryRepository.findByCode(code).orElseThrow(
                    () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
            );
            layers.add( category.getName() );
        }
        return layers;
    }

    // Single Code String -> Layer
    // - ex. F -> [ '프로그래밍', 'Java', 'Spring Boot', 'JPA' ]
    public List<String> singleCodeToLayer( String singleString ){
        List<String> layers = new ArrayList<>();
        Category category = categoryRepository.findByCode(singleString).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        while( category != null ){
            layers.add( category.getName() );
            if( category.getParent() != null ) {
                category = category.getParent();
            }
            else category = null;
        }
        Collections.reverse( layers );
        return layers;
    }

    // Single Code String -> Full Code String
    // - ex. F -> TDSF
    public String singleToFullCode( String singleString ){
        StringBuilder fullCode = new StringBuilder();
        Category category = categoryRepository.findByCode(singleString).orElseThrow(
                () -> new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND.getMessage())
        );
        while( category != null ){
            fullCode.append( category.getCode() );
            if( category.getParent() != null ) {
                category = category.getParent();
            }
            else category = null;
        }
        return fullCode.reverse().toString();
    }
}
