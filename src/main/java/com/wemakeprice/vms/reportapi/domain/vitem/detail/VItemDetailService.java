package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;

public interface VItemDetailService {
    String deleteVItemDetail(Long vItemId);
    VItemInfo.VItemDetailInfo updateVItemDetail(VItemCommand.UpdateVItemDetailRequest command);
    VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest command, Long vItemId);
}
