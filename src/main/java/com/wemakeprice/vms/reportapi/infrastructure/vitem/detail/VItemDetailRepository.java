package com.wemakeprice.vms.reportapi.infrastructure.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VItemDetailRepository extends JpaRepository<VItemDetail,Long> {
    Optional<VItemDetail> findVItemDetailById(Long id);
}
