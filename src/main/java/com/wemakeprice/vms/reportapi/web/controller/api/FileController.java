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
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    @GetMapping("/{fileId:.+}")
    @ResponseBody
    public CommonResponse<Resource> fileDownload(@PathVariable Long fileId) throws FileNotFoundException {

        var file = imageFacade.serveFile(fileId);
        return CommonResponse.success(file);
    }
}
