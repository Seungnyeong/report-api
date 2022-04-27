package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.VItemFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vitem")
public class VItemController {

    private final VItemFacade vItemFacade;

    @PostMapping
    public CommonResponse registerVItem(@RequestBody VItemDto.RegisterVItemRequest request) {
        var command = request.toCommand();
        var vItem = vItemFacade.registerVItem(command);
        return CommonResponse.success(vItem);
    }

    @GetMapping("/{vItemId}")
    public CommonResponse retrieve(@PathVariable("vItemId") Long id) {
        var vItemInfo = vItemFacade.retrieveVItem(id);
        return CommonResponse.success(vItemInfo);

    }

    @GetMapping
    public  CommonResponse retrieveAll() {
        var vItemInfoList = vItemFacade.retrieveVItemList();
        return CommonResponse.success(vItemInfoList);
    }

    @DeleteMapping("/{vItemId}")
    public CommonResponse deleteVItem(@PathVariable("vItemId") Long id) {
        var response = vItemFacade.deleteVItem((id));
        return CommonResponse.success(response);
    }

    @PatchMapping
    public CommonResponse updateVItem(@RequestBody VItemDto.UpdateVItemRequest request) {
        var response = vItemFacade.updateVItem(request.toCommand());
        return CommonResponse.success(response);
    }


}
