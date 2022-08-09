package com.wemakeprice.vms.reportapi.domain.report.image;

import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportReader;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportOptionImageServiceImpl implements  ReportOptionImageService {

    private final ReportOptionImageStore reportOptionImageStore;
    private final FileStorageService fileStorageService;
    private final ReportReader reportReader;
    private final ReportOptionReader reportOptionReader;
    private final ReportOptionImageReader reportOptionImageReader;

    @Transactional
    @Override
    public ReportInfo.ReportOptionImageInfo store(ReportCommand.GenerateReportOptionImageRequest command, Long reportId, MultipartFile file) throws IOException {
        var report = reportReader.findById(reportId);
        var reportOption  = reportOptionReader.findById(command.getReport_option_id());
        var fileInfo = fileStorageService.storeFile(file);
        var initReportOptionImage = command.toEntity(report, reportOption, fileInfo);
        var reportOptionImage = reportOptionImageStore.store(initReportOptionImage);
        return new ReportInfo.ReportOptionImageInfo(reportOptionImage, fileInfo.getFileUrl());
    }

    @Transactional
    @Override
    public void deleteImage(Long reportOptionImageId, Long reportId) {
        var report = reportReader.findById(reportId);
        var reportOptionImage = reportOptionImageReader.findByIdAndReport(reportOptionImageId, report);
        reportOptionImageStore.delete(reportOptionImage);
    }

    @Override
    public ReportInfo.ReportOptionImageInfo retrieveReportOptionImage(Long reportOptionImageId) {
        var reportOptionImage = reportOptionImageReader.findById(reportOptionImageId);
        return new ReportInfo.ReportOptionImageInfo(reportOptionImage, null);
    }
}
