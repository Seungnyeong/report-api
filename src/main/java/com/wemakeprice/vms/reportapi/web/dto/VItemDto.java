package com.wemakeprice.vms.reportapi.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

public class VItemDto {

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RegisterVItemRequest {
        private String vCategoryName;
        private String vSubCategoryName;
        private String vDetail;
        private List<RegisterVItemWithDetail> vItemDetailList;

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
    public static class RegisterVItemWithDetail {
        private String detail;

        public VItemCommand.RegisterVItemDetailRequest toCommand() {
            return VItemCommand.RegisterVItemDetailRequest.builder()
                    .detail(detail)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RegisterVItemDetail {

        private Long vItemId;
        private String detail;

        public VItemCommand.RegisterVItemDetailRequest toCommand() {
            return VItemCommand.RegisterVItemDetailRequest.builder()
                    .detail(detail)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class UpdateVItemRequest {

        private Long vItemId;

        private String vCategoryName;

        private String vSubCategoryName;

        private String vDetail;

        public VItemCommand.UpdateVItemRequest toCommand() {
            return VItemCommand.UpdateVItemRequest.builder()
                    .id(vItemId)
                    .vCategoryName(vCategoryName)
                    .vSubCategoryName(vSubCategoryName)
                    .vDetail(vDetail)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class UpdateVItemDetailRequest {

        private Long vItemDetailId;
        private String detail;

        public VItemCommand.UpdateVItemDetailRequest toCommand() {
            return VItemCommand.UpdateVItemDetailRequest.builder()
                    .id(vItemDetailId)
                    .detail(detail)
                    .build();
        }
    }
}
