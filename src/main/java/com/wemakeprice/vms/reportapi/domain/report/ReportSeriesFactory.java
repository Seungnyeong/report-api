package com.wemakeprice.vms.reportapi.domain.report;

import java.util.List;

public interface ReportSeriesFactory {
    List<ReportInfo.ReportOptionGroupInfo> store(ReportCommand.GenerateReportRequest request, Report report);
}