package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

public interface VItemDetailGroupStore {
    VItemDetailGroup store(VItemDetailGroup vItemDetailGroup);
    String delete(VItemDetailGroup vItemDetailGroup);
}
