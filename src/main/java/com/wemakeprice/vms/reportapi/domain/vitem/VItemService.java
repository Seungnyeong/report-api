package com.wemakeprice.vms.reportapi.domain.vitem;

import java.util.List;

public interface VItemService {

    // Insert
    VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest command);

    // 하나만 조회
    VItemInfo.Main retrieveVItem(Long vItemId);

    // 전체 조회
    List<VItemInfo.Main> retrieveVItemList();

    // Delete
    String deleteVItem(Long vItemId);

    // Update
    VItemInfo.Main updateVItem(VItemCommand.UpdateVItemRequest command);

}
