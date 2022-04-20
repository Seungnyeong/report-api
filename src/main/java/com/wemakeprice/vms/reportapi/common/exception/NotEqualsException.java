package com.wemakeprice.vms.reportapi.common.exception;

import com.wemakeprice.vms.reportapi.common.response.ErrorCode;

public class NotEqualsException extends BaseException {

    public NotEqualsException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public NotEqualsException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotEqualsException(String errorMsg) {
        super(errorMsg, ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public NotEqualsException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
