package com.wemakeprice.vms.reportapi.infrastructure.report.optionGroup;

import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportOptionGroupStoreImpl implements ReportOptionGroupStore {
    private final ReportOptionGroupRepository reportOptionGroupRepository;

    @Override
    public ReportOptionGroup store(ReportOptionGroup reportOptionGroup) {
        return reportOptionGroupRepository.save(reportOptionGroup);
    }
}
