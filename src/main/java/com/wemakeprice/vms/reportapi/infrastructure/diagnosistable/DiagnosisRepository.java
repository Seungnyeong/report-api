package com.wemakeprice.vms.reportapi.infrastructure.diagnosistable;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiagnosisRepository extends JpaRepository<DiagnosisTable, Long> {
    Optional<DiagnosisTable> findVItemById(Long id);
}
