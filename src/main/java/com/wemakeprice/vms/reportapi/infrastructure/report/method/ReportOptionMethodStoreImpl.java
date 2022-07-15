package com.wemakeprice.vms.reportapi.infrastructure.report.method;

import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethodStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportOptionMethodStoreImpl implements ReportOptionMethodStore {

    private final ReportOptionMethodRepository reportOptionMethodRepository;

    @Override
    public ReportOptionMethod save(ReportOptionMethod reportOptionMethod) {
        return reportOptionMethodRepository.save(reportOptionMethod);
    }
}
