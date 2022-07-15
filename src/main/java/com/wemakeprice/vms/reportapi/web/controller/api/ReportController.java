package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.report.ReportFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.web.dto.ReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
@Api(tags = "보고서")
public class ReportController {

    private final ReportFacade reportFacade;

    @ApiOperation(value = "레포트 파일 생성", notes = "개발전")
    @GetMapping("/print/{diagnosis_table_id}")
    public CommonResponse reportTest(@PathVariable Long diagnosis_table_id) throws Docx4JException, JAXBException, FileNotFoundException {
        reportFacade.printReport(diagnosis_table_id);
        return CommonResponse.success("보고서 생성 성공");
    }

    @ApiOperation(value = "레포트 조회", notes = "리포트 조회")
    @GetMapping
    public CommonResponse retrieveReport(@RequestParam Long DiagnosisTableId) {
        var response = reportFacade.retrieveReport(DiagnosisTableId);
        return CommonResponse.success(response);
    }

    @ApiOperation(value = "레포트 파일 저장", notes = "레포트 저장")
    @PostMapping
    public CommonResponse createReport(@RequestBody @Valid ReportDto.GenerateReportRequest request) {
        var command = request.toCommand();
        var response = reportFacade.generateReport(command);
        return CommonResponse.success(response);
    }

}
