package com.mzcteam01.mzcproject01be.common.base.category.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {
    private String name;
    private String description;
    private String code;
    private Integer parentId;
}
