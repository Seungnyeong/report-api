package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.vitem.VItemFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemCommand;
import com.wemakeprice.vms.reportapi.web.dto.VItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/vitem")
public class VItemController {

    private final VItemFacade vItemFacade;

    @PostMapping
    public CommonResponse registerVItem(@RequestBody VItemDto.RegisterRequest request) {
        var command = VItemCommand.RegisterVItemRequest.builder()
                .vCategoryName(request.getVCategoryName())
                .vSubCategoryName(request.getVSubCategoryName())
                .vDetail(request.getVDetail())
                .build();
        var response = vItemFacade.registerVItem(command);
        return CommonResponse.success(response);
    }
}
