package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VItemRepository extends JpaRepository<VItem, Long> {
    Optional<VItem> findVItemById(Long id);
    List<VItem> findAll();
}
