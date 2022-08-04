package com.wemakeprice.vms.reportapi.domain.vitem;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

public class VItemInfo {
    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Main {
        private final Long id;
        private final String vCategoryName;
        private final Integer vCategoryCode;;
        private final String vDetail;
        private final String caseTag;
        private final String respondTag;
        private final Integer ordering;
        private final List<VItemDetailGroupInfo> vItemDetailInfoGroupList;

        public Main(VItem vItem, List<VItemDetailGroupInfo> vItemDetailInfoGroupList) {
            this.id = vItem.getId();
            this.vCategoryName = vItem.getVCategoryName();
            this.vCategoryCode = vItem.getVCategoryCode();
            this.vDetail = vItem.getVDetail();
            this.caseTag = vItem.getCaseTag();
            this.respondTag = vItem.getRespondTag();
            this.ordering = vItem.getOrdering();
            this.vItemDetailInfoGroupList = vItemDetailInfoGroupList;
        }
    }

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class VItemDetailGroupInfo {
        private final Long id;
        private final VItemDetailGroup.VGroupGrade vGroupGrade;
        private final String vGroupName;
        private final Integer vGroupCode;
        private final Integer ordering;
        private final List<VItemDetailInfo> vItemDetailList;

        public VItemDetailGroupInfo(VItemDetailGroup vItemDetailGroup, List<VItemDetailInfo> vItemDetailList) {
            this.id = vItemDetailGroup.getId();
            this.vGroupGrade = vItemDetailGroup.getVGroupGrade();
            this.vGroupName = vItemDetailGroup.getVGroupName();
            this.vGroupCode = vItemDetailGroup.getVGroupCode();
            this.ordering = vItemDetailGroup.getOrdering();
            this.vItemDetailList = vItemDetailList;
        }

        public VItemDetailGroup toEntity(VItem vItem) {
            return VItemDetailGroup.builder()
                    .vItem(vItem)
                    .vGroupName(vGroupName)
                    .vGroupCode(vGroupCode)
                    .ordering(ordering)
                    .build();
        }

    }

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class VItemDetailInfo {
        private final Long id;
        private final String detail;
        private final List<String> respond;

        public VItemDetailInfo(VItemDetail vItemDetail) {
            this.id = vItemDetail.getId();
            this.detail = vItemDetail.getVDetail();
            this.respond = vItemDetail.getRespond();
        }

        public VItemDetail toEntity(VItemDetailGroup vItemDetailGroup) {
            return VItemDetail.builder()
                    .vDetail(detail)
                    .vItemDetailGroup(vItemDetailGroup)
                    .respond(respond)
                    .build();
        }
    }
}
