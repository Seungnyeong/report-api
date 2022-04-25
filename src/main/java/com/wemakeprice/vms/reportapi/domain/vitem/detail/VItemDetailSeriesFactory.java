package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;

import java.util.List;

public interface VItemDetailSeriesFactory {
    List<VItemDetail> store(VItemCommand.RegisterVItemRequest request, VItem item);

}
