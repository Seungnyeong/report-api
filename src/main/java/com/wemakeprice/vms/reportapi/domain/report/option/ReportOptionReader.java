package com.wemakeprice.vms.reportapi.domain.report.option;

import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;

public interface ReportOptionReader {
    ReportOption findById(Long reportOptionId);
}
