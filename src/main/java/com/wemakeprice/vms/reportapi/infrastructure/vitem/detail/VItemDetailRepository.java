package com.wemakeprice.vms.reportapi.infrastructure.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VItemDetailRepository extends JpaRepository<VItemDetail,Long> {
    Optional<VItemDetail> findVItemDetailById(Long id);
}
