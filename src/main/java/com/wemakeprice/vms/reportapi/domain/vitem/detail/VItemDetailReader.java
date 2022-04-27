package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;

import java.util.List;

public interface VItemDetailReader {
    VItemDetail getVItemDetail(Long vItemDetailId);
    List<VItemDetail> getAllDetailsGroupBy(VItemDetailGroup vItemDetailGroup);
}
