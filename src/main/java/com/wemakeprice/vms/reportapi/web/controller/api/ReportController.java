package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
public class ReportController {

    @ApiOperation(value = "레포트 생성", notes = "개발전")
    @PostMapping
    public CommonResponse reportTest(@RequestParam String test) {
        log.info("controller test");
        return CommonResponse.success(test);
    }

}
