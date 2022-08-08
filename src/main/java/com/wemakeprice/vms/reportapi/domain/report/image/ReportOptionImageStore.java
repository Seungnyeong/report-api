package com.wemakeprice.vms.reportapi.domain.report.image;

public interface ReportOptionImageStore {
    ReportOptionImage store(ReportOptionImage reportOptionImage);
    void delete(ReportOptionImage reportOptionImage);
}
