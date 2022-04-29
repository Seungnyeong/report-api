package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.detail.VItemDetailFacade;
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
@RequestMapping("/api/v1/vitem/detail")
@Api(tags = "진단 가이드 세부사항")
public class VItemDetailController {

    private final VItemDetailFacade vItemDetailFacade;


    @GetMapping("/{vItemDetailGroupId}")
    @ApiOperation(value = "진단 가이드 세부사항 아이템 검색", notes = "진단 가이드 세부사항 아이템 검색")
    public CommonResponse retrieveAllDetails(@PathVariable("vItemDetailGroupId") Long id) {
        var response = vItemDetailFacade.retrieveDetailInfo(id);
        return CommonResponse.success(response);
    }

    @PostMapping("/{vItemDetailGroupId}")
    @ApiOperation(value = "진단 가이드 세부사항 아이템 등록", notes = "진단 가이드 세부사항 아이템 등록")
    public CommonResponse registerVItemGroup(@RequestBody VItemDto.RegisterVItemDetail request, @PathVariable("vItemDetailGroupId") Long vItemDetailGroupId) {
        var response = vItemDetailFacade.registerVItemDetail(request.toCommand(), vItemDetailGroupId);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{vItemDetailId}")
    @ApiOperation(value = "진단 가이드 세부사항 아이템 삭제", notes = "진단 가이드 세부사항 아이템 삭제")
    public CommonResponse deleteVItemDetail(@PathVariable("vItemDetailId") Long vItemDetailId) {
        var response = vItemDetailFacade.deleteVItemDetail(vItemDetailId);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{vItemDetailId}/respond")
    @ApiOperation(value = "진단 가이드 세부사항 대응방안 삭제", notes = "진단 가이드 세부사항 대응방안 삭제")
    public CommonResponse deleteVItemDetailRespond(@PathVariable("vItemDetailId") Long vItemDetailId, @RequestParam int index) {
        var response = vItemDetailFacade.removeVItemDetailRespond(vItemDetailId, index);
        return CommonResponse.success(response);
    }

    @PatchMapping
    @ApiOperation(value = "진단 가이드 세부사항 대응방안 추가", notes = "진단 가이드 세부사항 대응방안 추가")
    public CommonResponse updateVItemDetail(@RequestBody  VItemDto.UpdateVItemDetailRequest request) {
        var response = vItemDetailFacade.updateVItemDetail(request.toCommand());
        return CommonResponse.success(response);

    }
}
