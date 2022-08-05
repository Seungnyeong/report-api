package com.wemakeprice.vms.reportapi.application.report;

import com.wemakeprice.vms.reportapi.docx.DocxInfo;
import com.wemakeprice.vms.reportapi.docx.DocxService;
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

    public DocxInfo printReport(Long diagnosisTableId) throws Exception {
        var diagnosisTable = diagnosisTableService.getDiagnosisTable(diagnosisTableId);
        var report = reportService.retrieveReport(diagnosisTable);
        var rePortFilePath = docxService.createReport(report);
        var savedPath = reportService.updateReportFilePath(rePortFilePath, report.getId());
        return DocxInfo.builder()
                .file(new File(savedPath))
                .fileName(report.getTitle() + "." + report.getFileExtension())
                .build();
    }

    public ReportInfo.ReportPassword getDecReportPassword(Long reportId) {
        return reportService.getReportPassword(reportId);
    }

    public ReportInfo.Main getReportMeta(Long reportId) {
        return reportService.getReportMeta(reportId);
    }
}
