package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {
    CATEGORY_NOT_FOUND("해당하는 카테고리가 존재하지 않습니다" ),
    CATEGORY_CODE_ALREADY_EXIST( "해당하는 카테고리 코드가 이미 존재합니다" ),
    CATEGORY_IS_ROOT( "해당 카테고리는 이미 최상위 카테고리입니디" );

    private String message;
}
