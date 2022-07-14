package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;

public interface ReportReader {
    Report findById(Long reportId);
    Report findByDiagnosisTable(DiagnosisTable diagnosisTable);
}
