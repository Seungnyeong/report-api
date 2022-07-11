package com.wemakeprice.vms.reportapi.infrastructure.report;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportSeriesFactory;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionStore;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroupStore;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSeriesFactoryImpl implements ReportSeriesFactory {

    private final ReportOptionStore reportOptionStore;
    private final ReportOptionGroupStore reportOptionGroupStore;

    @Override
    public List<ReportInfo.ReportOptionGroupInfo> store(ReportCommand.GenerateReportRequest command, Report report) {
        var reportOptionGroupList = command.getReportOptionGroupRequestList();
        if (CollectionUtils.isEmpty(reportOptionGroupList)) return Collections.emptyList();
        return reportOptionGroupList.stream()
                .map(reportOptionGroup -> {
                    var initReportOptionGroup = reportOptionGroup.toEntity(report, new VItemDetailGroup());
                    var reportOptionGroupEntity = reportOptionGroupStore.store(initReportOptionGroup);

                    var reportOptionInfoList = reportOptionGroup.getGenerateReportOptionGroupRequests().stream()
                            .map(optionInfo -> {
                                var initOption = optionInfo.toEntity(reportOptionGroupEntity);
                                reportOptionStore.save(initOption);
                                return new ReportInfo.ReportOptionInfo(initOption);
                            }).collect(Collectors.toList());
                    return new ReportInfo.ReportOptionGroupInfo(reportOptionGroupEntity, new VItemDetailGroup(), reportOptionInfoList);
                }).collect(Collectors.toList());
    }
}
