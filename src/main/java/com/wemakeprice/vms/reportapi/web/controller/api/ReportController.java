package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {

    @PostMapping
    public CommonResponse reportTest() {
        return CommonResponse.success("Report Controller");
    }

}
