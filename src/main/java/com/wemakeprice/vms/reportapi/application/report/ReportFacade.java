package com.wemakeprice.vms.reportapi.application.report;

import com.wemakeprice.vms.reportapi.docx.DocxService;
import com.wemakeprice.vms.reportapi.docx.FileInfo;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTableService;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportFacade {
    private final ReportService reportService;
    private final DiagnosisTableService diagnosisTableService;
    private final DocxService docxService;

    public ReportInfo.Main generateReport(ReportCommand.GenerateReportRequest command) {
        var diagnosisTable = diagnosisTableService.getDiagnosisTable(command.getDiagnosisTableId());
        return reportService.generateReport(command, diagnosisTable);
    }

    public ReportInfo.Main retrieveReport(Long diagnosisTableId) {
        var diagnosisTable = diagnosisTableService.getDiagnosisTable(diagnosisTableId);
        return reportService.retrieveReport(diagnosisTable);
    }

    public FileInfo printReport(Long diagnosisTableId) throws Exception {
        var diagnosisTable = diagnosisTableService.getDiagnosisTable(diagnosisTableId);
        var report = reportService.retrieveReport(diagnosisTable);
        var rePortFilePath = docxService.createReport(report);
        var savedPath = reportService.updateReportFilePath(rePortFilePath, report.getId());
        return FileInfo.builder()
                .file(new File(savedPath))
                .fileName(report.getTitle() + ".docx")
                .build();
    }

    public ReportInfo.ReportPassword getDecReportPassword(Long reportId) {
        return reportService.getReportPassword(reportId);
    }

    public ReportInfo.Main getReportMeta(Long reportId) {
        return reportService.getReportMeta(reportId);
    }


    public void updateReport(ReportCommand.GenerateReportRequest command) {
        reportService.updateReportMain(command);
    }

    public void updateReportOption(ReportCommand.GenerateReportOptionGroupRequest command) {
        reportService.updateReportOption(command);
    }

    public void updateReportOptionMethod(ReportCommand.GenerateReportOptionMethodRequest command) {
        reportService.updateReportMethodOption(command);
    }
}
