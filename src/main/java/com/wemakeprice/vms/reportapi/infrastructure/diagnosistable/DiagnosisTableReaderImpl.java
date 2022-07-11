package com.wemakeprice.vms.reportapi.infrastructure.diagnosistable;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTableReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiagnosisTableReaderImpl implements DiagnosisTableReader {

    private final DiagnosisRepository diagnosisRepository;

    @Override
    public DiagnosisTable findById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
