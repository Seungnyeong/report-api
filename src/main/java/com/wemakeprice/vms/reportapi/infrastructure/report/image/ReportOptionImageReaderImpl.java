package com.wemakeprice.vms.reportapi.infrastructure.report.image;


import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImageReader;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportOptionImageReaderImpl implements ReportOptionImageReader {

    private final ReportOptionImageRepository reportOptionImageRepository;

    @Override
    public List<ReportInfo.ReportOptionImageInfo> finByReportOption(ReportOption reportOption) {
        var reportImageList = reportOptionImageRepository.findAllByReportOption(reportOption);
        return reportImageList.stream().map(image -> new ReportInfo.ReportOptionImageInfo(image, null)).collect(Collectors.toList());
    }

    @Override
    public ReportOptionImage findByIdAndReport(Long reportOptionImageId, Report report) {
        return reportOptionImageRepository.findByIdAndReport(reportOptionImageId, report).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ReportOptionImage findById(Long reportOptionImageId) {
        return reportOptionImageRepository.findById(reportOptionImageId).orElseThrow(EntityNotFoundException::new);
    }
}
