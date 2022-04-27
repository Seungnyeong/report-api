package com.wemakeprice.vms.reportapi.application.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemService;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetailService;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemFacade {
    private final VItemService vItemService;
    private final VItemDetailGroupService vItemDetailGroupService;

    public VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest command) {
        return vItemService.registerVItem(command);
    }

    public VItemInfo.Main retrieveVItem(Long vItemId) {
        return vItemService.retrieveVItem(vItemId);
    }

    public List<VItemInfo.Main> retrieveVItemList() {
        return vItemService.retrieveVItemList();
    }

    public String deleteVItem(Long vItemId) {
        return vItemService.deleteVItem(vItemId);
    }

    public VItemInfo.Main updateVItem(VItemCommand.UpdateVItemRequest request) {
        return vItemService.updateVItem(request);
    }
}
