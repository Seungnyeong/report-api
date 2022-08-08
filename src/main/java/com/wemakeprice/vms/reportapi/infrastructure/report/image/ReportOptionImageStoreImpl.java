package com.wemakeprice.vms.reportapi.infrastructure.report.image;

import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImageStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportOptionImageStoreImpl implements ReportOptionImageStore {

    private final ReportOptionImageRepository reportOptionImageRepository;

    @Override
    public ReportOptionImage store(ReportOptionImage reportOptionImage) {
        return reportOptionImageRepository.save(reportOptionImage);
    }

    @Override
    public void delete(ReportOptionImage reportOptionImage) {
        reportOptionImageRepository.delete(reportOptionImage);
    }
}
