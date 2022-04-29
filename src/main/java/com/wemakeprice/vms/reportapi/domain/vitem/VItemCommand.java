package com.wemakeprice.vms.reportapi.domain.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
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
        private final Integer vCategoryCode;;
        private final String vDetail;
        private final List<RegisterVItemGroupRequest> vItemGroupRequestList;
        private final String caseTag;
        private final String respondTag;
        private final Integer ordering;

        public VItem toEntity() {
            return VItem.builder()
                    .vCategoryName(vCategoryName)
                    .vCategoryCode(vCategoryCode)
                    .ordering(ordering)
                    .respondTag(respondTag)
                    .vDetail(vDetail)
                    .caseTag(caseTag)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterVItemGroupRequest {
        private final String vGroupName;
        private final Integer vGroupCode;
        private final Integer ordering;
        private final List<RegisterVItemDetailRequest> vItemDetailRequestList;

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
    @Builder
    @ToString
    public static class RegisterVItemDetailRequest {
        private final String detail;

        public VItemDetail toEntity(VItemDetailGroup vItemDetailGroup) {
            return VItemDetail.builder()
                    .vItemDetailGroup(vItemDetailGroup)
                    .vDetail(detail)
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
        private final String vDetail;
        private final String caseTag;
        private final String respondTag;
        private final Integer ordering;

        public VItem toEntity() {
            return VItem.builder()
                    .vCategoryName(vCategoryName)
                    .vCategoryCode(vCategoryCode)
                    .vDetail(vDetail)
                    .caseTag(caseTag)
                    .respondTag(respondTag)
                    .ordering(ordering)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateVItemDetailRequest {
        private final Long id;
        private final String detail;
        private final List<String> respond;

        public VItemDetail toEntity() {
            return VItemDetail.builder()
                    .vDetail(detail)
                    .respond(respond)
                    .build();
        }
    }
}
