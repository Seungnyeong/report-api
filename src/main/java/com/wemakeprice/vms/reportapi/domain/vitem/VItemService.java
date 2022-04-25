package com.wemakeprice.vms.reportapi.domain.vitem;

import java.util.List;

public interface VItemService {

    // Insert
    VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest command);
    VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest command, Long vItemId);

    // Search
    VItemInfo.Main retrieveVItem(Long vItemId);
    List<VItemInfo.Main> retrieveVItemList();

    // Delete
    String deleteVItem(Long vItemId);
    String deleteVItemDetail(Long vItemId);

    // Update
    VItemInfo.Main updateVItem(VItemCommand.UpdateVItemRequest command);
    VItemInfo.VItemDetailInfo updateVItemDetail(VItemCommand.UpdateVItemDetailRequest command);
    VItemInfo.Main changeVItemGradeToHigh();
    VItemInfo.Main changeVItemGradeToLow();
    VItemInfo.Main changeVItemGradeToMedium();

}
