package com.wemakeprice.vms.reportapi.infrastructure.report.method;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethodReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportOptionMethodReaderImpl implements ReportOptionMethodReader {

    private final ReportOptionMethodRepository reportOptionMethodRepository;

    @Override
    public ReportOptionMethod findById(Long reportOptionMethodId) {
        return reportOptionMethodRepository.findById(reportOptionMethodId).orElseThrow(EntityNotFoundException::new);
    }
}