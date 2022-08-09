package com.wemakeprice.vms.reportapi.domain.report.image;

import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOError;
import java.io.IOException;

public interface ReportOptionImageService {
    ReportInfo.ReportOptionImageInfo store(ReportCommand.GenerateReportOptionImageRequest command, Long reportId, MultipartFile file) throws IOException;
    void deleteImage(Long reportOptionImageId, Long ReportId);
    ReportInfo.ReportOptionImageInfo retrieveReportOptionImage(Long reportOptionImageId);
}
