package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonErrorCode {
    RELATED_ENTITY_EXISTED("해당 엔티티와 연관된 다른 엔티티가 존재합니다" );

    private String message;
}
