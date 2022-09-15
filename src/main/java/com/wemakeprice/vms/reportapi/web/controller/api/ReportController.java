package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.application.report.ReportFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.common.response.ErrorCode;
import com.wemakeprice.vms.reportapi.domain.users.User;
import com.wemakeprice.vms.reportapi.jira.JiraApiService;
import com.wemakeprice.vms.reportapi.jira.JiraLoginCommand;
import com.wemakeprice.vms.reportapi.web.dto.ReportDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Slf4j
@Api(tags = "보고서")
public class ReportController {

    private final ReportFacade reportFacade;
    private final JiraApiService jiraApiService;

    @ApiOperation(value = "레포트 파일 생성/다운로드", notes = "레포트 파일 생성 및 다운로드")
    @GetMapping(value = "/print/{diagnosis_table_id}" )
    public ResponseEntity<InputStreamResource> printReport(@PathVariable Long diagnosis_table_id) throws Exception {
        var reportFile = reportFacade.printReport(diagnosis_table_id);

        return   ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,  String.format("attachment;filename=\"%1$s\";", URLEncoder.encode(reportFile.getFileName(), StandardCharsets.UTF_8)))
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
    @PostMapping("/password")
    public CommonResponse getReportFilePassword(@RequestBody ReportDto.ReportPasswordRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var command = JiraLoginCommand.builder()
                .username(user.getUsername())
                .password(request.getPassword())
                .build();

        var isAuth = jiraApiService.isAuthUser(command);

        if (isAuth) {
            var response = reportFacade.getDecReportPassword(request.getReport_id());
            return CommonResponse.success(response);
        }

        return CommonResponse.fail(ErrorCode.COMMON_AUTH_ERROR);
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

    @ApiOperation(value = "레포트 상세내용 추가", notes = "레포트 상세내용 추가")
    @PostMapping("/create/option")
    public CommonResponse createReportOption(@RequestBody ReportDto.RegisterReportOptionGroup request) {
        var command = request.toCommand();
        var result = reportFacade.createReportOption(command, request.getReport_id());
        return CommonResponse.success(result);
    }

    @ApiOperation(value = "레포트 메소드 수정", notes = "레포트 메소드 수정")
    @PutMapping("/edit/method")
    public CommonResponse editReportOptionMethod(@RequestBody ReportDto.UpdateReportOptionMethod request) {
        var command = request.toCommand();
        reportFacade.updateReportOptionMethod(command);
        return CommonResponse.success("success");
    }

    @ApiOperation(value = "레포트 옵션그룹 삭제", notes = "레포트 옵션그룹 삭제")
    @DeleteMapping("/option/{report_option_group_id}")
    public CommonResponse deleteReportOptionGroup(@PathVariable Long report_option_group_id) {
        reportFacade.deleteReportOptionGroup(report_option_group_id);
        return CommonResponse.success("success");
    }

    @ApiOperation(value = "레포트 옵션그룹 삭제", notes = "레포트 옵션그룹 삭제")
    @DeleteMapping("/option/method/{report_option_method_id}")
    public CommonResponse deleteReportOptionMethod(@PathVariable Long report_option_method_id) {
        reportFacade.deleteReportOptionMethod(report_option_method_id);
        return CommonResponse.success("success");
    }

    @ApiOperation(value = "레포트 옵션 메소드 추가", notes = "레포트 옵션 메소드 추가")
    @PostMapping("/option/method/{report_option_id}")
    public CommonResponse addReportOptionMethod(@PathVariable Long report_option_id, @RequestBody ReportDto.RegisterReportOptionMethod request) {
        var command = request.toCommand();
        var reportOptionMethodInfo = reportFacade.addReportOptionMethod(command, report_option_id);
        return CommonResponse.success(reportOptionMethodInfo);
    }

    @ApiOperation(value = "레포트 객체 삭제", notes = "레포트 객체 삭제")
    @DeleteMapping("/delete/{report_id}")
    public CommonResponse deleteReportEntity(@PathVariable Long report_id) {
        reportFacade.deleteReportEntity(report_id);
        return CommonResponse.success(null);
    }
}
