package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;

import java.util.List;

public interface ReportReader {
    Report findById(Long reportId);
    Report findByDiagnosisTable(DiagnosisTable diagnosisTable);
    List<ReportInfo.ReportOptionGroupInfo> reportOptionGroupList(Report report);
}
