package com.wemakeprice.vms.reportapi.domain.vitem.detail;

public interface VItemDetailStore {
    VItemDetail store(VItemDetail vItemDetail);
    String delete(VItemDetail vItemDetail);
}
