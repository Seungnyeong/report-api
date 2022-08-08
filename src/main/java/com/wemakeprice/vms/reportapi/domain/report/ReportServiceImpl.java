package com.wemakeprice.vms.reportapi.domain.report;

import static com.wemakeprice.vms.reportapi.config.CryptoKeyConfig.KEY;
import static com.wemakeprice.vms.reportapi.config.CryptoKeyConfig.IV;

import com.wemakeprice.vms.reportapi.common.utils.crypto.WmpCryptoUtils;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethodReader;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final ReportStore reportStore;
    private final ReportSeriesFactory reportSeriesFactory;
    private final ReportReader reportReader;
    private final ReportOptionMethodReader reportOptionMethodReader;
    private final ReportOptionReader reportOptionReader;

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

    @Transactional
    @Override
    public String updateReportFilePath(Path path, Long reportId) {
        var report = reportReader.findById(reportId);
        var filePath = path.toAbsolutePath().toString();
        report.updateFilePath(filePath);
        return filePath;
    }

    @Transactional
    @Override
    public ReportInfo.ReportPassword getReportPassword(Long reportId) {
        var report = reportReader.findById(reportId);
        var encPassword = report.getReportPassword();
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        var decPassword = WmpCryptoUtils.decrypt(encPassword, keySpec, IV.substring(0,16).getBytes(StandardCharsets.UTF_8));
        return new ReportInfo.ReportPassword(decPassword);
    }

    @Transactional
    @Override
    public ReportInfo.Main getReportMeta(Long reportId) {
        var report = reportReader.findById(reportId);
        return new ReportInfo.Main(report, null);
    }

    @Transactional
    @Override
    public void updateReportMain(ReportCommand.GenerateReportRequest command) {
        var report = reportReader.findById(command.getId());
        report.updateReport(command);
    }

    @Transactional
    @Override
    public void updateReportOption(ReportCommand.GenerateReportOptionGroupRequest command) {
        var reportOption = reportOptionReader.findById(command.getId());
        reportOption.updateReportOption(command);
    }

    @Transactional
    @Override
    public void updateReportMethodOption(ReportCommand.GenerateReportOptionMethodRequest command) {
        var reportOptionMethod = reportOptionMethodReader.findById(command.getId());
        reportOptionMethod.updateReportOptionMethod(command);
    }
}
