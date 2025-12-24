package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationErrorCode {
    ORGANIZATION_NOT_FOUND( "해당하는 아카데미를 찾을 수 없습니다" );

    private String message;
}
