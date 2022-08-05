package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;

import java.nio.file.Path;


public interface ReportService {
    ReportInfo.Main generateReport(ReportCommand.GenerateReportRequest command, DiagnosisTable diagnosisTable);
    ReportInfo.Main retrieveReport(DiagnosisTable diagnosisTable);
    ReportInfo.ReportPassword getReportPassword(Long reportId);
    String updateReportFilePath(Path path, Long reportId);
    ReportInfo.Main getReportMeta(Long reportId);
}
