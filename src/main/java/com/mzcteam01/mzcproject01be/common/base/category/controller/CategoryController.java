package com.mzcteam01.mzcproject01be.common.base.category.controller;

import com.mzcteam01.mzcproject01be.common.base.category.dto.request.CreateCategoryRequest;
import com.mzcteam01.mzcproject01be.common.base.category.dto.response.CategoryResponse;
import com.mzcteam01.mzcproject01be.common.base.category.service.CategoryService;
import com.mzcteam01.mzcproject01be.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    @Operation( summary = "카테고리 생성" )
    public ResponseEntity<Void> createCategory(
        @RequestBody CreateCategoryRequest request,
        @AuthenticationPrincipal AuthUser authUser
    ){
        categoryService.create( request, authUser.getId() );
        return ResponseEntity.ok( null );
    }

    @DeleteMapping("/{id}")
    @Operation( summary = "카테고리 삭제" )
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id,
            @AuthenticationPrincipal AuthUser authUser
    ){
        categoryService.delete( id, authUser.getId());
        return ResponseEntity.ok( null );
    }

    @GetMapping("/lecture")
    public ResponseEntity<List<CategoryResponse>> getLectureCategoryTree() {
        List<CategoryResponse> tree = categoryService.getLectureCategoryTree();
        System.out.println(tree);
        return ResponseEntity.ok(tree);
    }
}
