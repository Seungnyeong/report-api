package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class VItemInfo {

    @Getter
    @ToString
    public static class Main {
        private final String vCategoryName;
        private final Integer vCategoryCode;
        private final String vSubCategoryName;
        private final Integer vSubCategoryCode;
        private final String vDetail;
        private List<VItemDetailInfo> vItemDetailsList;
        private final String caseTag;
        private final VItem.VGrade vGrade;
        private final String respondTag;
        private Integer ordering;

        public Main(VItem vItem, List<VItemDetailInfo> vItemDetailInfoList) {
            this.vCategoryName = vItem.getVCategoryName();
            this.vCategoryCode = vItem.getVCategoryCode();
            this.vSubCategoryName = vItem.getVSubCategoryName();
            this.vSubCategoryCode = vItem.getVSubCategoryCode();
            this.vDetail = vItem.getVDetail();
            this.vItemDetailsList = vItemDetailInfoList;
            this.caseTag = vItem.getCaseTag();
            this.vGrade = vItem.getVGrade();
            this.respondTag = vItem.getRespondTag();
        }

        public Main(VItem vItem) {
            this.vCategoryName = vItem.getVCategoryName();
            this.vCategoryCode = vItem.getVCategoryCode();
            this.vSubCategoryName = vItem.getVSubCategoryName();
            this.vSubCategoryCode = vItem.getVSubCategoryCode();
            this.vDetail = vItem.getVDetail();
            this.caseTag = vItem.getCaseTag();
            this.vGrade = vItem.getVGrade();
            this.respondTag = vItem.getRespondTag();
        }
    }

    @Getter
    @ToString
    public static class VItemDetailInfo {
        private final String detail;

        public VItemDetailInfo(VItemDetail vItemDetail) {
            this.detail = vItemDetail.getVDetail();
        }

        public VItemDetail toEntity(VItem vItem) {
            return VItemDetail.builder()
                    .vDetail(detail)
                    .vItem(vItem)
                    .build();
        }
    }
}
