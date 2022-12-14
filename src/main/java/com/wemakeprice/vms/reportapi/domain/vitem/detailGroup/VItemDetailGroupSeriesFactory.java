package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;

import java.util.List;

public interface VItemDetailGroupSeriesFactory {
    List<VItemInfo.VItemDetailInfo> store(VItemCommand.RegisterVItemGroupRequest request, VItemDetailGroup vItemDetailGroup);
}
