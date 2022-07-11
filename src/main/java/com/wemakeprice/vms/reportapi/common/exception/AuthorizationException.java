package com.wemakeprice.vms.reportapi.common.exception;


import com.wemakeprice.vms.reportapi.common.response.ErrorCode;

public class AuthorizationException extends BaseException {

    public AuthorizationException() {
        super(ErrorCode.COMMON_AUTH_ERROR);
    }

    public AuthorizationException(String message) {
        super(message, ErrorCode.COMMON_AUTH_ERROR);
    }
}
