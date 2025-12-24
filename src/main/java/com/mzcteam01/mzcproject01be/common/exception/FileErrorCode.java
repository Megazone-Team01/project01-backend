package com.mzcteam01.mzcproject01be.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileErrorCode {
    FILE_NOT_FOUND( "파일을 찾을 수 없습니다" ),
    FILE_SIZE_EXCEEDED( "업로드 가능 용량을 초과했습니다" ),
    FILE_UPLOAD_ERROR( "파일 업로드 중 에러가 발생했습니다" ),
    FILE_DELETE_ERROR( "파일 삭제 중 에러가 발생했습니다" ),
    FILE_EXTENSION_UNSUPPORTED( "지원하지 않는 파일 형식입니다" );

    private String message;
}
