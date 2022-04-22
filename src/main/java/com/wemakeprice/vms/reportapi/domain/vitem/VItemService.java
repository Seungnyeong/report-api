package com.wemakeprice.vms.reportapi.domain.vitem;

import java.util.List;

public interface VItemService {
    VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest request);
    VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest request, Long vItemId);
    VItemInfo.Main retrieveVItem(Long vItemId);
    List<VItemInfo.Main> retrieveVItemList();

    String deleteVItem(Long vItemId);
    String deleteVItemDetail(Long vItemId);
}
