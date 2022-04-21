package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
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
    public static class RegisterVItemDetailRequest {
        private final String detail;

        public VItemDetail toEntity(VItem vItem) {
            return VItemDetail.builder()
                    .vItem(vItem)
                    .vDetail(detail)
                    .build();
        }
    }
}
