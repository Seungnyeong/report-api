package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;

import java.util.List;

public interface VItemDetailService {
    String deleteVItemDetail(Long vItemDetailId);
    VItemInfo.VItemDetailInfo updateVItemDetail(VItemCommand.UpdateVItemDetailRequest command);
    VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest command, Long vItemDetailId);
    List<VItemInfo.VItemDetailInfo> retrieveAllDetail(Long vItemDetailId);
    VItemInfo.VItemDetailInfo removeVItemDetailRespond(Long vItemDetailId, int index);
}
