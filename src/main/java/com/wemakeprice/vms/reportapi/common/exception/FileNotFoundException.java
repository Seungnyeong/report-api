package com.wemakeprice.vms.reportapi.common.exception;

import com.wemakeprice.vms.reportapi.common.response.ErrorCode;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException() {
        super(ErrorCode.COMMON_FILE_ERROR);
    }

    public FileNotFoundException(String message) {
        super(message, ErrorCode.COMMON_FILE_ERROR);
    }
}
