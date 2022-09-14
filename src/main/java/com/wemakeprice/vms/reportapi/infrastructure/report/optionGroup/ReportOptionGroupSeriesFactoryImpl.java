package com.wemakeprice.vms.reportapi.infrastructure.report.optionGroup;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethodStore;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionStore;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroupSeriesFactory;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroupStore;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class ReportOptionGroupSeriesFactoryImpl implements ReportOptionGroupSeriesFactory {
    private final ReportOptionStore reportOptionStore;
    private final ReportOptionGroupStore reportOptionGroupStore;
    private final ReportOptionMethodStore reportOptionMethodStore;
    private final VItemDetailGroupService vItemDetailGroupService;

    @Override
    public ReportInfo.ReportOptionGroupInfo store(Report report, ReportCommand.GenerateReportGroupRequest command) {
        var vItemDetailGroup = vItemDetailGroupService.getOneVItemDetailGroup(command.getVItemDetailGroupId());
        var reportOptionGroup = reportOptionGroupStore.store(command.toEntity(report, vItemDetailGroup));
        var reportOptionDetailList = command.getGenerateReportOptionGroupRequestList();

        var reportOptionInfoList =  reportOptionDetailList.stream().map(reportOptionDetail -> {
            var newReportOption = reportOptionStore.save(reportOptionDetail.toEntity(reportOptionGroup));
            var reportOptionMethodInfoList = reportOptionDetail.getGenerateReportOptionMethodRequestList().stream().map(optionMethodInfo -> {
                var initOptionMethod = optionMethodInfo.toEntity(newReportOption);
                var reportOptionMethod = reportOptionMethodStore.save(initOptionMethod);
                return new ReportInfo.ReportOptionMethodInfo(reportOptionMethod);
            }).collect(Collectors.toList());
            return new ReportInfo.ReportOptionInfo(newReportOption, reportOptionMethodInfoList, null);
        }).collect(Collectors.toList());
        return new ReportInfo.ReportOptionGroupInfo(reportOptionGroup, new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup, null), reportOptionInfoList);
    }
}
