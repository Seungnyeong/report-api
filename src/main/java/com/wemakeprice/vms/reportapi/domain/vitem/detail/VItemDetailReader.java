package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;

import java.util.List;

public interface VItemDetailReader {
    VItemDetail getVItemDetail(Long vItemDetailId);
    List<VItemInfo.VItemDetailInfo> getVItemDetail(VItem vItem);

}
