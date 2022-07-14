package com.wemakeprice.vms.reportapi.application.report;

import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.image.FileStorageService;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageFacade {

    private final ReportOptionImageService reportOptionImageService;
    private final FileStorageService fileStorageService;

    public ReportInfo.ReportOptionImageInfo storeImageFile(ReportCommand.GenerateReportOptionImageRequest command, Long reportId, MultipartFile file) throws IOException {
        return reportOptionImageService.store(command, reportId, file);
    }

    public Resource serveFile(Long fileId) throws FileNotFoundException {
        return null;
//        return fileStorageService.loadFileAsResource();
    }
}
