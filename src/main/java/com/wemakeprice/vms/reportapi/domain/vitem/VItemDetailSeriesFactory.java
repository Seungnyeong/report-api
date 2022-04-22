package com.wemakeprice.vms.reportapi.domain.vitem;

import java.util.List;

public interface VItemDetailSeriesFactory {
    List<VItemDetail> store(VItemCommand.RegisterVItemRequest request, VItem item);

}
