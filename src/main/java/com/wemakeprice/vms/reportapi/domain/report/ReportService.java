package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;


public interface ReportService {
    ReportInfo.Main generateReport(ReportCommand.GenerateReportRequest command, DiagnosisTable diagnosisTable);
    ReportInfo.Main retrieveReport(DiagnosisTable diagnosisTable);
}
