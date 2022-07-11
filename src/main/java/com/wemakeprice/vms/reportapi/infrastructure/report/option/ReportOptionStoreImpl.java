package com.wemakeprice.vms.reportapi.infrastructure.report.option;

import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportOptionStoreImpl implements ReportOptionStore {
    private final ReportOptionRepository reportOptionRepository;


    @Override
    public ReportOption save(ReportOption reportOption) {
        return reportOptionRepository.save(reportOption);
    }
}
