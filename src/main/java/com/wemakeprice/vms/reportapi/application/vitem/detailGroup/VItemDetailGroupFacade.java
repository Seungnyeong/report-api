package com.wemakeprice.vms.reportapi.application.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemDetailGroupFacade {
    private final VItemDetailGroupService vItemDetailGroupService;

    public VItemInfo.VItemDetailGroupInfo registerVItemDetailGroup(VItemCommand.RegisterVItemGroupRequest command, Long vItemId) {
        return vItemDetailGroupService.registerVItemGroup(command, vItemId);
    }

    public String deleteVItemDetailGroup(Long vItemGroupDetailId) {
        return vItemDetailGroupService.deleteVItemGroup(vItemGroupDetailId);
    }

    public void changeGrade(Long vItemGroupDetailId, VItemDetailGroup.VGroupGrade type) {
        if (type == VItemDetailGroup.VGroupGrade.LOW) vItemDetailGroupService.changeGradeToLow(vItemGroupDetailId);
        if (type == VItemDetailGroup.VGroupGrade.HIGH) vItemDetailGroupService.changeGradeToHigh(vItemGroupDetailId);
        if (type == VItemDetailGroup.VGroupGrade.MEDIUM) vItemDetailGroupService.changeGradeToMedium(vItemGroupDetailId);
    }

    public VItemInfo.VItemDetailGroupInfo updateVItemDetailGroup(VItemDetailGroupCommand.UpdateVItemDetailGroupRequest request) {
        return vItemDetailGroupService.updateVItemGroup(request);
    }
}
