package com.wemakeprice.vms.reportapi.domain.vitem;

public interface VItemStore {
    VItem store(VItem vItemEntity);
    String delete(VItem vItemEntity);
}
