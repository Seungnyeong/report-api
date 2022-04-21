package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItem;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemStore;
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
    public VItemDetail store(VItemDetail vItemDetail) {
        return null;
    }
}
