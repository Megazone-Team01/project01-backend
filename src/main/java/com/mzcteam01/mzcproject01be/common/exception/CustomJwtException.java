package com.mzcteam01.mzcproject01be.common.exception;

public class CustomJwtException extends JwtException {
    private final int status;

    public CustomJwtException(JwtErrorCode errorCode) {

        super(errorCode);
        this.status = errorCode.getStatus();
    }

    public int getStatus() {
        return status;
    }

}
