package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTableInfo;

import javax.transaction.Transactional;
import java.nio.file.Path;


public interface ReportService {
    ReportInfo.Main generateReport(ReportCommand.GenerateReportRequest command, DiagnosisTableInfo.Main diagnosisTableInfo);
    ReportInfo.Main retrieveReport(DiagnosisTableInfo.Main diagnosisTableInfo);
    ReportInfo.ReportPassword getReportPassword(Long reportId);
    String updateReportFilePath(Path path, Long reportId);
    ReportInfo.Main getReportMeta(Long reportId);
    ReportInfo.ReportOptionGroupInfo createReportOptionGroup(ReportCommand.GenerateReportGroupRequest command, Long reportId, Long vItemDetailGroupId);
    ReportInfo.ReportOptionMethodInfo addOptionMethod(ReportCommand.GenerateReportOptionMethodRequest command, Long reportOptionId);
    void updateReportMain(ReportCommand.GenerateReportRequest command);
    void updateReportOption(ReportCommand.GenerateReportOptionGroupRequest command);
    void updateReportMethodOption(ReportCommand.GenerateReportOptionMethodRequest command);
    void deleteReportOptionGroup(Long reportOptionGroupId);
    void deleteReportOptionMethod(Long reportOptionMethodId);
}
