package com.wemakeprice.vms.reportapi.domain.diagnosis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisTableService{
    private final DiagnosisTableReader diagnosisTableReader;

    @Transactional
    @Override
    public DiagnosisTableInfo.Main getDiagnosisTable(Long id) {
        var table = diagnosisTableReader.findById(id);
        return new DiagnosisTableInfo.Main(table);
    }
}
