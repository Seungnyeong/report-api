package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class VItemCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterVItemRequest {
        private final String vCategoryName;
        private final String vSubCategoryName;
        private final String vDetail;
    }
}
