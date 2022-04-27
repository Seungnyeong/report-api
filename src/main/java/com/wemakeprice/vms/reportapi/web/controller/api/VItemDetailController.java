package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.detail.VItemDetailFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vitem/detail")
public class VItemDetailController {

    private final VItemDetailFacade vItemDetailFacade;


    @GetMapping("/{vItemDetailGroupId}")
    public CommonResponse retrieveAllDetails(@PathVariable("vItemDetailGroupId") Long id) {
        var response = vItemDetailFacade.retrieveDetailInfo(id);
        return CommonResponse.success(response);
    }

    @PostMapping("/{vItemDetailGroupId}")
    public CommonResponse registerVItemGroup(@RequestBody VItemDto.RegisterVItemDetail request, @PathVariable("vItemDetailGroupId") Long vItemDetailGroupId) {
        var response = vItemDetailFacade.registerVItemDetail(request.toCommand(), vItemDetailGroupId);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{vItemDetailId}")
    public CommonResponse deleteVItemDetail(@PathVariable("vItemDetailId") Long vItemDetailId) {
        var response = vItemDetailFacade.deleteVItemDetail(vItemDetailId);
        return CommonResponse.success(response);
    }

    @PatchMapping
    public CommonResponse updateVItemDetail(@RequestBody VItemDto.UpdateVItemDetailRequest request) {
        var response = vItemDetailFacade.updateVItemDetail(request.toCommand());
        return CommonResponse.success(response);

    }
}
