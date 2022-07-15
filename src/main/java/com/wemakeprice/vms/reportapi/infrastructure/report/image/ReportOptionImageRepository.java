package com.wemakeprice.vms.reportapi.infrastructure.report.image;

import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportOptionImageRepository extends JpaRepository<ReportOptionImage, Long> {
    List<ReportOptionImage> findAllByReportOption(ReportOption reportOption);
}
