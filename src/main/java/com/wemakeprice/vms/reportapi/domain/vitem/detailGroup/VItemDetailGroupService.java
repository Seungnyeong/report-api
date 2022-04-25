package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;

public interface VItemDetailGroupService {
    VItemInfo.VItemDetailGroupInfo registerVItemGroup(VItemCommand.RegisterVItemGroupRequest command, Long vItemId);
}
