package com.wemakeprice.vms.reportapi.domain.report;

public interface ReportReader {
    interface ReportStore {
        Report store(Report report);
    }
}
