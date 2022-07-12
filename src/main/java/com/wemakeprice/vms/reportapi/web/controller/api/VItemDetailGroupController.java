package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.detailGroup.VItemDetailGroupFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vitem/group")
@Api(tags = "진단 가이드 중분류")
public class VItemDetailGroupController {

    private final VItemDetailGroupFacade vItemDetailGroupFacade;

    @PostMapping("/{vItemId}")
    @ApiOperation(value = "진단 가이드 중분류 아이템 검색", notes = "진단 가이드 중분류 아이템 등록")
    public CommonResponse registerVItemGroup(@RequestBody VItemDto.RegisterVItemDetailGroup request, @PathVariable("vItemId") Long id) {
        var response = vItemDetailGroupFacade.registerVItemDetailGroup(request.toCommand(), id);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{vItemDetailGroupId}")
    @ApiOperation(value = "진단 가이드 중분류 아이템 삭제", notes = "진단 가이드 중분류 아이템 삭제")
    public CommonResponse deleteVItemDetailGroup(@PathVariable("vItemDetailGroupId") Long id) {
        var response = vItemDetailGroupFacade.deleteVItemDetailGroup(id);
        return CommonResponse.success(response);
    }

    @PatchMapping("/grade/{vItemDetailGroupId}")
    @ApiOperation(value = "진단 가이드 중분류 등급 변경", notes = "진단 가이드 중분류 등급 변경")
    public CommonResponse changeGradeGroup(@PathVariable("vItemDetailGroupId") Long id, VItemDetailGroup.VGroupGrade type) {
        vItemDetailGroupFacade.changeGrade(id, type);
        return CommonResponse.success(String.format("%s 으로 변경 되었습니다.", type));
    }

    @PatchMapping
    @ApiOperation(value = "진단 가이드 중분류 아이템 업데이트", notes = "진단 가이드 중분류 아이템 업데이트")
    public CommonResponse updateVItemDetailGroup(@RequestBody VItemDto.UpdateVItemDetailGroupRequest request) {
        var response = vItemDetailGroupFacade.updateVItemDetailGroup(request.toCommand());
        return CommonResponse.success(response);
    }
}
