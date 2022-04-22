package com.wemakeprice.vms.reportapi.domain.vitem;

import java.util.List;

public interface VItemReader {
    VItem getVItemBy(Long vItemId);
    List<VItemInfo.VItemDetailInfo> getVItemDetail(VItem vItem);
    List<VItem> getVItemList();
}
