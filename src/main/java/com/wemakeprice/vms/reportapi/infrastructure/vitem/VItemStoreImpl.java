package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VItemStoreImpl implements VItemStore {

    private final VItemRepository vItemRepository;

    @Override
    public VItem store(VItem vItemEntity) {
        return vItemRepository.save(vItemEntity);
    }

    @Override
    public String delete(VItem vItemEntity) {
        vItemRepository.delete(vItemEntity);
        return vItemEntity.getVDetail();
    }
}
