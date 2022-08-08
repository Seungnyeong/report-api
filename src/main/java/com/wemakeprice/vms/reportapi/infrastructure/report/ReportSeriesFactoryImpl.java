package com.wemakeprice.vms.reportapi.infrastructure.report;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.ReportSeriesFactory;
import com.wemakeprice.vms.reportapi.domain.report.image.FileResponse;
import com.wemakeprice.vms.reportapi.domain.report.image.FileStorageService;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImageStore;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethodStore;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionStore;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroupStore;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportSeriesFactoryImpl implements ReportSeriesFactory {

    private final ReportOptionStore reportOptionStore;
    private final ReportOptionMethodStore reportOptionMethodStore;
    private final ReportOptionGroupStore reportOptionGroupStore;
    private final VItemDetailGroupService vItemDetailGroupService;

    @Override
    public List<ReportInfo.ReportOptionGroupInfo> store(ReportCommand.GenerateReportRequest command, Report report) {
        var reportOptionGroupList = command.getReportOptionGroupRequestList();
        if (CollectionUtils.isEmpty(reportOptionGroupList)) return Collections.emptyList();
        return reportOptionGroupList.stream()
                .map(reportOptionGroup -> {
                    var vItemDetailGroup = vItemDetailGroupService.getOneVItemDetailGroup(reportOptionGroup.getVItemDetailGroupId());
                    var initReportOptionGroup = reportOptionGroup.toEntity(report, vItemDetailGroup);
                    var reportOptionGroupEntity = reportOptionGroupStore.store(initReportOptionGroup);
                    var reportOptionInfoList = reportOptionGroup.getGenerateReportOptionGroupRequestList().stream()
                            .map(optionInfo -> {
                                var initOption = optionInfo.toEntity(reportOptionGroupEntity);
                                var reportOption = reportOptionStore.save(initOption);
                                var reportOptionMethodInfoList = optionInfo.getGenerateReportOptionMethodRequestList().stream().map(optionMethodInfo -> {
                                    var initOptionMethod = optionMethodInfo.toEntity(reportOption);
                                    var reportOptionMethod = reportOptionMethodStore.save(initOptionMethod);
                                    return new ReportInfo.ReportOptionMethodInfo(reportOptionMethod);
                                }).collect(Collectors.toList());
                                return new ReportInfo.ReportOptionInfo(initOption, reportOptionMethodInfoList, null);
                            }).collect(Collectors.toList());
                    return new ReportInfo.ReportOptionGroupInfo(reportOptionGroupEntity, new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup,
                            vItemDetailGroup.getVItemDetailsList().stream().map(VItemInfo.VItemDetailInfo::new).collect(Collectors.toList())), reportOptionInfoList);
                }).collect(Collectors.toList());
    }
}
