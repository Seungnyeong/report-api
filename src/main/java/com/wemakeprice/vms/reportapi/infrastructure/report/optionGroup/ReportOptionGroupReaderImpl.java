package com.wemakeprice.vms.reportapi.infrastructure.report.optionGroup;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroupReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportOptionGroupReaderImpl implements ReportOptionGroupReader {
    private final ReportOptionGroupRepository reportOptionGroupRepository;
    @Override
    public ReportOptionGroup find(Long Id) {
        return reportOptionGroupRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
    }
}
