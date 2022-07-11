package com.wemakeprice.vms.reportapi.infrastructure.report.option;

import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportOptionRepository extends JpaRepository<ReportOption, Long> {
}
