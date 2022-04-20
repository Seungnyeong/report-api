package com.wemakeprice.vms.reportapi.common.exception;


import com.wemakeprice.vms.reportapi.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
