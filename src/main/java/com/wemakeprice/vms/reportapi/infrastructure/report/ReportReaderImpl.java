package com.wemakeprice.vms.reportapi.infrastructure.report;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportReaderImpl implements ReportReader {
    private final ReportRepository reportRepository;

    @Override
    public Report findById(Long reportId) {
        return reportRepository.findById(reportId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Report findByDiagnosisTable(DiagnosisTable diagnosisTable) {
        return reportRepository.findByDiagnosisTable(diagnosisTable);
    }
}
