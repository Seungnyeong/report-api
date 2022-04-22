package com.wemakeprice.vms.reportapi.application.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemFacade {
    private final VItemService vItemService;

    public VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest request) {
        return vItemService.registerVItem(request);
    }

    public VItemInfo.Main retrieveVItem(Long vItemId) {
        return vItemService.retrieveVItem(vItemId);
    }

    public VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest request, Long vItemId) {
        return vItemService.registerVItemDetail(request, vItemId);
    }

    public List<VItemInfo.Main> retrieveVItemList() {
        return vItemService.retrieveVItemList();
    }

    public String deleteVItem(Long vItemId) {
        return vItemService.deleteVItem(vItemId);
    }

    public String deleteVItemDetail(Long vItemDetailId) {
        return vItemService.deleteVItemDetail(vItemDetailId);
    }
}
