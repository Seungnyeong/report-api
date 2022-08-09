package com.wemakeprice.vms.reportapi.web.controller.api;

import com.wemakeprice.vms.reportapi.annotation.ValidFile;
import com.wemakeprice.vms.reportapi.application.report.ImageFacade;
import com.wemakeprice.vms.reportapi.common.response.CommonResponse;
import com.wemakeprice.vms.reportapi.domain.report.image.FileStorageService;
import com.wemakeprice.vms.reportapi.web.dto.ImageFileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report/file")
@Slf4j
@Api(tags = "보고서 이미지 업로드")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal=true)
public class FileController {

    private final ImageFacade imageFacade;

    @ApiOperation(value = "보고서 이미지 파일 업로드", notes = "이미지 파일 업로드입니다.")
    @PostMapping
    public CommonResponse fileUpload(@Validated @ValidFile @ModelAttribute ImageFileDto.RegisterFile request) {
        var files = request.getFiles().stream().map(file -> {
            var command = file.toCommand();
            try {
                return imageFacade.storeImageFile(command, request.getReport_id(), file.getFile());
            } catch (IOException e) {
                return null;
            }
        }).collect(Collectors.toList());

        return CommonResponse.success(files);
    }

    @ApiOperation(value = "보고서 이미지 파일 다운로드", notes = "이미지 파일 다운로드.")
    @GetMapping("/{fileId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable Long fileId) throws FileNotFoundException {
        var fileInfo = imageFacade.serve(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        new String(fileInfo.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\";")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(fileInfo.getFile().length())
                .body(new InputStreamResource(new FileInputStream(fileInfo.getFile())));
    }

    @ApiOperation(value = "레포트 파일 이미지 삭제", notes = "레포트 파일 이미지 삭제")
    @DeleteMapping("/{report_id}/{report_option_image_id}")
    public CommonResponse deleteReportOptionImage(@PathVariable Long report_option_image_id , @PathVariable Long report_id ) {
        imageFacade.delete(report_option_image_id, report_id);
        return CommonResponse.success("삭제 성공");
    }
}
