package com.wemakeprice.vms.reportapi.domain.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
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
        private final Integer vCategoryCode;
        private final String vSubCategoryName;
        private final Integer vSubCategoryCode;
        private final String vDetail;
        private final List<RegisterVItemDetailRequest> vItemDetailRequestList;
        private final String caseTag;
        private final String respondTag;
        private final Integer ordering;
        private final VItem.VGrade vGrade;

        public VItem toEntity() {
            return VItem.builder()
                    .vCategoryName(vCategoryName)
                    .vCategoryCode(vCategoryCode)
                    .vSubCategoryName(vSubCategoryName)
                    .vSubCategoryCode(vCategoryCode)
                    .vDetail(vDetail)
                    .caseTag(caseTag)
                    .respondTag(respondTag)
                    .ordering(ordering)
                    .vGrade(vGrade)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateVItemRequest {
        private final Long id;
        private final String vCategoryName;
        private final Integer vCategoryCode;
        private final String vSubCategoryName;
        private final Integer vSubCategoryCode;
        private final String vDetail;
        private final String caseTag;
        private final String respondTag;
        private final Integer ordering;
        private final VItem.VGrade vGrade;

        public VItem toEntity() {
            return VItem.builder()
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
