package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VItemRepository extends JpaRepository<VItem, Long> {
}
