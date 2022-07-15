package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportStore reportStore;
    private final ReportSeriesFactory reportSeriesFactory;
    private final ReportReader reportReader;

    @Transactional
    @Override
    public ReportInfo.Main generateReport(ReportCommand.GenerateReportRequest command, DiagnosisTable diagnosisTable) {
        var initReport = command.toEntity(diagnosisTable);
        var report = reportStore.store(initReport);
        var reportOptionGroup = reportSeriesFactory.store(command, report);
        return new ReportInfo.Main(report, reportOptionGroup);
    }

    @Transactional
    @Override
    public ReportInfo.Main retrieveReport(DiagnosisTable diagnosisTable) {
        var report = reportReader.findByDiagnosisTable(diagnosisTable);
        var reportOptionGroup = reportReader.reportOptionGroupList(report);
        return new ReportInfo.Main(report, reportOptionGroup);
    }
}
