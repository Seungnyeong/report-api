package com.wemakeprice.vms.reportapi.web.dto;

import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

public class VItemDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterVItemRequest {
        private String vCategoryName;
        private String vSubCategoryName;
        private String vDetail;
        private List<RegisterVItemDetail> vItemDetailList;

        public VItemCommand.RegisterVItemRequest toCommand() {
            return VItemCommand.RegisterVItemRequest.builder()
                    .vCategoryName(vCategoryName)
                    .vSubCategoryName(vSubCategoryName)
                    .vDetail(vDetail)
                    .vItemDetailRequestList(vItemDetailList.stream().map(registerVItemDetail -> VItemCommand.RegisterVItemDetailRequest.builder()
                            .detail(registerVItemDetail.detail)
                            .build()).collect(Collectors.toList()))
                    .build();
        }

    }

    @Getter
    @Setter
    @ToString
    public static class RegisterVItemDetail {
        private String vItemId;
        private String detail;

        public VItemCommand.RegisterVItemDetailRequest toCommand() {
            return VItemCommand.RegisterVItemDetailRequest.builder()
                    .detail(detail)
                    .build();
        }
    }
}
