package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import java.util.List;

public interface VItemReader {
    VItem getVItemBy(Long vItemId);
    List<VItemInfo.VItemDetailInfo> getVItemDetail(VItem vItem);
    List<VItem> getVItemList();
}
