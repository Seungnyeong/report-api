package com.wemakeprice.vms.reportapi.application.report;

import com.wemakeprice.vms.reportapi.docx.FileInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.image.FileResponse;
import com.wemakeprice.vms.reportapi.domain.report.image.FileStorageService;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageFacade {

    private final ReportOptionImageService reportOptionImageService;

    public ReportInfo.ReportOptionImageInfo storeImageFile(ReportCommand.GenerateReportOptionImageRequest command, Long reportId, MultipartFile file) throws IOException {
        return reportOptionImageService.store(command, reportId, file);
    }

    public FileInfo serve(Long fileId) {
        var reportOptionImageInfo = reportOptionImageService.retrieveReportOptionImage(fileId);
        return FileInfo.builder()
                .file(new File(reportOptionImageInfo.getFilePath()))
                .fileName(reportOptionImageInfo.getImageName())
                .build();
    }

    public void delete(Long reportOptionImageId, Long reportId) {
        // 파일 IO 삭제도 필요할 수 있음.
        reportOptionImageService.deleteImage(reportOptionImageId, reportId);
    }
}
