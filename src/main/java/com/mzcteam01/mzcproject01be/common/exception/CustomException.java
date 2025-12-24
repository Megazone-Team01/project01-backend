package com.mzcteam01.mzcproject01be.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    public CustomException( String msg ){
        super( msg );
    }

    public CustomException( UserErrorCode userErrorCode ){
        super( userErrorCode.getMessage() );
    }
}
