package com.wemakeprice.vms.reportapi.infrastructure.vitem.detailGroup;


import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VItemDetailGroupRepository extends JpaRepository<VItemDetailGroup, Long> {
    Optional<VItemDetailGroup> findVItemDetailGroupById(Long id);
}
