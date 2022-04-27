package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetailCommand;

import java.util.List;

public interface VItemDetailGroupService {
    VItemInfo.VItemDetailGroupInfo registerVItemGroup(VItemCommand.RegisterVItemGroupRequest command, Long vItemId);
    String deleteVItemGroup(Long vItemDetailGroupId);
    VItemInfo.VItemDetailGroupInfo updateVItemGroup(VItemDetailGroupCommand.UpdateVItemDetailGroupRequest command);
    List<VItemInfo.VItemDetailGroupInfo> retrieveDetailGroupByVItemId(Long vItemId);

    void changeGradeToHigh(Long vItemDetailGroupId);
    void changeGradeToMedium(Long vItemDetailGroupId);
    void changeGradeToLow(Long vItemDetailGroupId);
}
