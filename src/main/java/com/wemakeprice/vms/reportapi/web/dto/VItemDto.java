package com.wemakeprice.vms.reportapi.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

public class VItemDto {

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RegisterVItemRequest {

        @NotNull(message = "vCategoryName 필수 입니다.")
        @Size(min = 1, max = 100, message = "최소 Size= 1, 최대 Size=3 입니다.")
        private String vCategoryName;

        @NotNull(message = "vCategoryCode 필수 입니다.")
        @Min(value = 1, message = "0보다 커야 합니다.")
        @Max(value = 999 , message = "999 이하입니다.")
        private Integer vCategoryCode;

        @NotNull(message = "vSubCategoryName 필수 입니다.")
        @Size(min = 1, max = 100, message = "최소 Size= 1, 최대 Size=100 입니다.")
        private String vSubCategoryName;

        @NotNull(message = "vSubCategoryCode 필수 입니다.")
        @Min(value = 1, message = "0보다 커야 합니다.")
        @Max(value = 999 , message = "999 이하입니다.")
        private Integer vSubCategoryCode;

        @Size(min = 0, max = 1000, message = "0에서 1000자 사이입니다.")
        private String vDetail;
        private String caseTag;
        private String respondTag;
        private Integer ordering;

        @NotNull(message = "vGrade 필수 입니다.")
        private VItem.VGrade vGrade;
        private List<RegisterVItemWithDetail> vItemDetailList;

        public VItemCommand.RegisterVItemRequest toCommand() {
            return VItemCommand.RegisterVItemRequest.builder()
                    .vCategoryName(vCategoryName)
                    .vCategoryCode(vCategoryCode)
                    .vSubCategoryName(vSubCategoryName)
                    .vSubCategoryCode(vSubCategoryCode)
                    .vDetail(vDetail)
                    .caseTag(caseTag)
                    .respondTag(respondTag)
                    .ordering(ordering)
                    .vGrade(vGrade)
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

        @NotEmpty(message = "detail 필수 입니다.")
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

        @NotNull(message = "vItemId 필수 입니다.")
        private Long vItemId;

        @NotNull(message = "detail 필수 입니다.")
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
        @NotNull(message = "id는 필수값입니다.")
        private Long vItemId;

        @NotNull(message = "vCategoryName 필수 입니다.")
        @Size(min = 1, max = 100, message = "최소 Size= 1, 최대 Size=3 입니다.")
        private String vCategoryName;

        @NotNull(message = "vCategoryCode 필수 입니다.")
        @Min(value = 1, message = "0보다 커야 합니다.")
        @Max(value = 999 , message = "999 이하입니다.")
        private Integer vCategoryCode;

        @NotNull(message = "vSubCategoryName 필수 입니다.")
        @Size(min = 1, max = 100, message = "최소 Size= 1, 최대 Size=100 입니다.")
        private String vSubCategoryName;

        @NotNull(message = "vSubCategoryCode 필수 입니다.")
        @Min(value = 1, message = "0보다 커야 합니다.")
        @Max(value = 999 , message = "999 이하입니다.")
        private Integer vSubCategoryCode;

        @Size(min = 0, max = 1000, message = "0에서 1000자 사이입니다.")
        private String vDetail;

        @Size(min = 1, max = 10, message = "최소 Size= 1, 최대 Size=100 입니다.")
        private String caseTag;
        private String respondTag;
        private Integer ordering;

        @NotNull(message = "vGrade 필수 입니다.")
        private VItem.VGrade vGrade;

        public VItemCommand.UpdateVItemRequest toCommand() {
            return VItemCommand.UpdateVItemRequest.builder()
                    .id(vItemId)
                    .vCategoryName(vCategoryName)
                    .vCategoryCode(vCategoryCode)
                    .vSubCategoryName(vSubCategoryName)
                    .vSubCategoryCode(vSubCategoryCode)
                    .vDetail(vDetail)
                    .caseTag(caseTag)
                    .respondTag(respondTag)
                    .ordering(ordering)
                    .vGrade(vGrade)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class UpdateVItemDetailRequest {

        @NotNull(message = "vItemId 필수 입니다.")
        private Long vItemDetailId;

        @Size(min = 0, max = 1000, message = "0에서 1000자 사이입니다.")
        private String detail;

        public VItemCommand.UpdateVItemDetailRequest toCommand() {
            return VItemCommand.UpdateVItemDetailRequest.builder()
                    .id(vItemDetailId)
                    .detail(detail)
                    .build();
        }
    }
}
