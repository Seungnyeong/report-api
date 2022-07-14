package com.wemakeprice.vms.reportapi.infrastructure.report.image;

import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportOptionImageRepository extends JpaRepository<ReportOptionImage, Long> {
}
