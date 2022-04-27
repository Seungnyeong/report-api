package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.detailGroup.VItemDetailGroupFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vitem/group")
public class VItemDetailGroupController {

    private final VItemDetailGroupFacade vItemDetailGroupFacade;

    @PostMapping("/{vItemId}")
    public CommonResponse registerVItemGroup(@RequestBody VItemDto.RegisterVItemDetailGroup request, @PathVariable("vItemId") Long id) {
        var response = vItemDetailGroupFacade.registerVItemDetailGroup(request.toCommand(), id);
        return CommonResponse.success(response);
    }

    @DeleteMapping("/{vItemDetailGroupId}")
    public CommonResponse deleteVitemDetailGroup(@PathVariable("vItemDetailGroupId") Long id) {
        var response = vItemDetailGroupFacade.deleteVItemDetailGroup(id);
        return CommonResponse.success(response);
    }

    @PatchMapping("/grade/{vItemDetailGroupId}")
    public CommonResponse changeGradeGroup(@PathVariable("vItemDetailGroupId") Long id, VItemDetailGroup.VGroupGrade type) {
        vItemDetailGroupFacade.changeGrade(id, type);
        return CommonResponse.success(String.format("%s 으로 변경 되었습니다.", type));
    }

    @PatchMapping("")
    public CommonResponse updateVItemDetailGroup(@RequestBody VItemDto.UpdateVItemDetailGroupRequest request) {
        var response = vItemDetailGroupFacade.updateVItemDetailGroup(request.toCommand());
        return CommonResponse.success(response);
    }
}
