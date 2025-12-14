package com.mzcteam01.mzcproject01be.common.exception;

public class CustomJwtException extends RuntimeException {
    private final int status;

    public CustomJwtException(JwtErrorCode errorCode) {

        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
    }

    public int getStatus() {
        return status;
    }

}
