package com.mzcteam01.mzcproject01be.common.base.category.controller;

import com.mzcteam01.mzcproject01be.common.base.category.dto.response.CategoryResponse;
import com.mzcteam01.mzcproject01be.common.base.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category" )
@RequiredArgsConstructor
@Tag( name = "Category Controller", description = "카테고리 관련 컨트롤러" )
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping()
    @Operation( summary = "카테고리 조회 API" )
    public ResponseEntity<List<CategoryResponse>> getCategory(
        @RequestParam(required = false) Integer parentId
    ){
        return ResponseEntity.ok( categoryService.findChildCategory( parentId ) );
    }
}
