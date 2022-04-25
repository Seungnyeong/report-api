package com.wemakeprice.vms.reportapi.domain.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;

import java.util.List;

public interface VItemSeriesFactory {
    List<VItemDetailGroup> store(VItemCommand.RegisterVItemRequest request, VItem item);
}
