package com.wemakeprice.vms.reportapi.infrastructure.report;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportReader;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<ReportInfo.ReportOptionGroupInfo> reportOptionGroupList(Report report) {
        var reportOptionGroupList = report.getReportOptionGroupsList();
        return reportOptionGroupList.stream()
                .map(reportOptionGroup -> {
                    var reportOptionList = reportOptionGroup.getReportOptionsList();
                    var vItemDetailGroup = reportOptionGroup.getVItemDetailGroup();
                    var reportOptionInfoList = reportOptionList.stream()
                            .map(reportOption -> {
                                var reportMethodList = reportOption.getReportOptionMethodList();
                                var reportMethodInfoList = reportMethodList.stream().map(ReportInfo.ReportOptionMethodInfo::new).collect(Collectors.toList());
                                return new ReportInfo.ReportOptionInfo(reportOption, reportMethodInfoList);
                            })
                            .collect(Collectors.toList());
                    return new ReportInfo.ReportOptionGroupInfo(reportOptionGroup, new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup, null), reportOptionInfoList);
                }).collect(Collectors.toList());
    }
}
