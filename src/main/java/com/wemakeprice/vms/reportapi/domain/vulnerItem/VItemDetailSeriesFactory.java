package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import java.util.List;

public interface VItemDetailSeriesFactory {
    List<VItemDetail> store(VItemCommand.RegisterVItemRequest request, VItem item);
}
