package com.wemakeprice.vms.reportapi.domain.vitem;

import java.util.List;

public interface VItemReader {
    VItem getVItemBy(Long vItemId);
    VItem getOne(Long vItemId);
    List<VItem> getVItemList();
    List<VItemInfo.VItemDetailGroupInfo> getVItemDetailGroupSeries(VItem item);
}
