package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.report.ReportFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.web.dto.ReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
@Api(tags = "보고서")
public class ReportController {
    //TODO 사용자 비밀번호 인증 관련하여 필요함 ( 지라 꺼 쓰면 될듯 )
    private final ReportFacade reportFacade;

    @ApiOperation(value = "레포트 파일 생성/다운로드", notes = "레포트 파일 생성 및 다운로드")
    @GetMapping(value = "/print/{diagnosis_table_id}")
    public ResponseEntity<InputStreamResource> printReport(@PathVariable Long diagnosis_table_id) throws Exception {
        var reportFile = reportFacade.printReport(diagnosis_table_id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        new String(reportFile.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\";")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(reportFile.getFile().length())
                .body(new InputStreamResource(new FileInputStream(reportFile.getFile())));
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

    @ApiOperation(value = "레포트 파일 암호 조회", notes = "레포트 암호")
    @GetMapping("/password/{report_id}")
    public CommonResponse getReportFilePassword(@PathVariable Long report_id, @RequestBody String password) {
        var response = reportFacade.getDecReportPassword(report_id);
        return CommonResponse.success(response);
    }

    @ApiOperation(value = "레포트 파일 메타 조회", notes = "레포트 메타")
    @GetMapping("/meta/{report_id}")
    public CommonResponse getReportMeta(@PathVariable Long report_id) {
        var response = reportFacade.getReportMeta(report_id);
        return CommonResponse.success(response);
    }

    @ApiOperation(value = "레포트 Meta 수정", notes = "레포트 Meta 수정")
    @PutMapping("/edit/main")
    public CommonResponse editReportMain(@RequestBody ReportDto.UpdateReportMainRequest request) {
        var command = request.toCommand();
        reportFacade.updateReport(command);
        return CommonResponse.success("success");
    }

    @ApiOperation(value = "레포트 상세내용 수정", notes = "레포트 상세내용 수정")
    @PutMapping("/edit/option")
    public CommonResponse editReportOption(@RequestBody ReportDto.UpdateReportOption request) {
        var command = request.toCommand();
        reportFacade.updateReportOption(command);
        return CommonResponse.success("success");
    }

    @ApiOperation(value = "레포트 메소드 수정", notes = "레포트 메소드 수정")
    @PutMapping("/edit/method")
    public CommonResponse editReportOptionMethod(@RequestBody ReportDto.UpdateReportOptionMethod request) {
        var command = request.toCommand();
        reportFacade.updateReportOptionMethod(command);
        return CommonResponse.success("success");
    }

}
