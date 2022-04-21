package com.wemakeprice.vms.reportapi.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class VItemDto {

    @Getter
    @ToString
    @Setter
    public static class RegisterRequest {
        private String vCategoryName;
        private String vSubCategoryName;
        private String vDetail;
    }
}
