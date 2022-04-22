package com.wemakeprice.vms.reportapi.domain.vitem;

public interface VItemDetailStore {
    VItemDetail store(VItemDetail vItemDetail);
    String delete(VItemDetail vItemDetail);
}
