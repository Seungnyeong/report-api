package com.wemakeprice.vms.reportapi.domain.vulnerItem;

public interface VItemStore {
    VItem store(VItem vItemEntity);
    String delete(VItem vItemEntity);
}
