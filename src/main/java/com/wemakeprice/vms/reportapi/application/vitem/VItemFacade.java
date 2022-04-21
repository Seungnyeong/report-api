package com.wemakeprice.vms.reportapi.application.vitem;

import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemFacade {
    private final VItemService vItemService;

    public String registerVItem(VItemCommand.RegisterVItemRequest request) {
        return vItemService.registerVItem(request);
    }
}
