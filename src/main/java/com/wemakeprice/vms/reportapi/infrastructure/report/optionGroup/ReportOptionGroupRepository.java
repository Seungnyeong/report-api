package com.wemakeprice.vms.reportapi.infrastructure.report.optionGroup;

import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportOptionGroupRepository extends JpaRepository<ReportOptionGroup, Long> {
}
