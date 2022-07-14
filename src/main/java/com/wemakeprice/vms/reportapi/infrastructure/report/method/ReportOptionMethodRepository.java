package com.wemakeprice.vms.reportapi.infrastructure.report.method;

import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportOptionMethodRepository extends JpaRepository<ReportOptionMethod, Long> {
}
