package com.mzcteam01.mzcproject01be.common.base.category.dto.response;

import com.mzcteam01.mzcproject01be.common.base.category.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    private Integer id;
    private String name;
    private Integer parentId;
    private String code;

    @Builder.Default
    private List<CategoryResponse> children = new ArrayList<>();

    public static CategoryResponse of( Category category ) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parentId(category.getParent() == null ? null : category.getParent().getId() )
                .code(category.getCode())
                .build();
    }
}
