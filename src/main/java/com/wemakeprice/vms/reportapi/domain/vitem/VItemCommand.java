package com.wemakeprice.vms.reportapi.domain.vitem;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class VItemCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterVItemRequest {
        private final String vCategoryName;
        private final String vSubCategoryName;
        private final String vDetail;
        private final List<RegisterVItemDetailRequest> vItemDetailRequestList;

        public VItem toEntity() {
            return VItem.builder()
                    .vCategoryName(vCategoryName)
                    .vSubCategoryName(vSubCategoryName)
                    .vDetail(vDetail)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateVItemRequest {
        private final Long id;
        private final String vCategoryName;
        private final String vSubCategoryName;
        private final String vDetail;

        public VItem toEntity() {
            return VItem.builder()
                    .vCategoryName(vCategoryName)
                    .vSubCategoryName(vSubCategoryName)
                    .vDetail(vDetail)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterVItemDetailRequest {
        private final String detail;

        public VItemDetail toEntity(VItem vItem) {
            return VItemDetail.builder()
                    .vItem(vItem)
                    .vDetail(detail)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateVItemDetailRequest {
        private final Long id;
        private final String detail;

        public VItemDetail toEntity() {
            return VItemDetail.builder()
                    .vDetail(detail)
                    .build();
        }
    }
}