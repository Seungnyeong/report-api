package com.wemakeprice.vms.reportapi.application.report;

import com.wemakeprice.vms.reportapi.docx.DocxService;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTableService;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportService;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

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

    public void printReport(Long diagnosisTableId) throws JAXBException, FileNotFoundException, Docx4JException {
        var diagnosisTable = diagnosisTableService.getDiagnosisTable(diagnosisTableId);
        var report = reportService.retrieveReport(diagnosisTable);
        docxService.createHeaderTemplate(report);
    }
}
