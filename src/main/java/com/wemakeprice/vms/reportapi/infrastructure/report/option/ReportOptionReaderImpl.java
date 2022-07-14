package com.wemakeprice.vms.reportapi.infrastructure.report.option;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOptionReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportOptionReaderImpl implements ReportOptionReader {

    private final ReportOptionRepository reportOptionRepository;

    @Override
    public ReportOption findById(Long reportOptionId) {
        return reportOptionRepository.findById(reportOptionId).orElseThrow(EntityNotFoundException::new);
    }
}
