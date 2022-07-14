package com.wemakeprice.vms.reportapi.infrastructure.report;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportReader;
import com.wemakeprice.vms.reportapi.domain.report.ReportStore;
import com.wemakeprice.vms.reportapi.infrastructure.diagnosistable.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportStoreImpl implements ReportStore {

    private final ReportRepository reportRepository;

    @Override
    public Report store(Report report) {
        return reportRepository.save(report);
    }
}
