package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class VItemDetailGroupCommand {

    @Getter
    @Builder
    @ToString
    public static class UpdateVItemDetailGroupRequest {
        private final Long id;
        private final String vGroupName;
        private final Integer vGroupCode;
        private final Integer ordering;

        public VItemDetailGroup toEntity() {
            return VItemDetailGroup.builder()
                    .vGroupName(vGroupName)
                    .vGroupCode(vGroupCode)
                    .ordering(ordering)
                    .build();
        }
    }
}
