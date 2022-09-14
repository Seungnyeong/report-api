package com.wemakeprice.vms.reportapi.domain.report.optionGroup;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;

public interface ReportOptionGroupSeriesFactory {
    ReportInfo.ReportOptionGroupInfo store(Report report, ReportCommand.GenerateReportGroupRequest command);
}
