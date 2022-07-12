package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.VItemFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vitem")
@Api(tags = "진단 가이드 대분류")
public class VItemController {

    private final VItemFacade vItemFacade;

    @PostMapping
    @ApiOperation(value = "진단 대분류 아이템 등록", notes = "진단 가이드 대분류 아이템 등록")
    public CommonResponse registerVItem(@RequestBody VItemDto.RegisterVItemRequest request) {
        var command = request.toCommand();
        var vItem = vItemFacade.registerVItem(command);
        return CommonResponse.success(vItem);
    }

    @GetMapping("/{vItemId}")
    @ApiOperation(value = "진단 대분류 아이템 조회", notes = "진단 가이드 대분류 아이템 조회")
    public CommonResponse retrieve(@PathVariable("vItemId") Long id) {
        var vItemInfo = vItemFacade.retrieveVItem(id);
        return CommonResponse.success(vItemInfo);

    }

    @GetMapping
    @ApiOperation(value = "진단 대분류 전체 조회", notes = "진단 가이드 대분류 전체 조회")
    public  CommonResponse retrieveAll() {
        var vItemInfoList = vItemFacade.retrieveVItemList();
        return CommonResponse.success(vItemInfoList);
    }

    @DeleteMapping("/{vItemId}")
    @ApiOperation(value = "진단 대분류 아이템 삭제", notes = "진단 가이드 대분류 아이템 삭제")
    public CommonResponse deleteVItem(@PathVariable("vItemId") Long id) {
        var response = vItemFacade.deleteVItem((id));
        return CommonResponse.success(response);
    }

    @PatchMapping
    @ApiOperation(value = "진단 대분류 아이템 업데이트", notes = "진단 가이드 대분류 아이템 업데이트")
    public CommonResponse updateVItem(@RequestBody VItemDto.UpdateVItemRequest request) {
        var response = vItemFacade.updateVItem(request.toCommand());
        return CommonResponse.success(response);
    }


}
