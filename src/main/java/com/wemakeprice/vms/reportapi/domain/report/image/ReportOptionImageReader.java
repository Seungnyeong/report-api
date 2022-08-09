package com.wemakeprice.vms.reportapi.domain.report.image;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportInfo;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import java.util.List;

public interface ReportOptionImageReader {
    List<ReportInfo.ReportOptionImageInfo> finByReportOption(ReportOption reportOption);
    ReportOptionImage findByIdAndReport(Long reportOptionImageId, Report report);
    ReportOptionImage findById(Long reportOptionImageId);
}
