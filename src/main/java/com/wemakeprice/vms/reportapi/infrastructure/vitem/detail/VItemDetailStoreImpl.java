package com.wemakeprice.vms.reportapi.infrastructure.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemDetailStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VItemDetailStoreImpl implements VItemDetailStore {

    private final VItemDetailRepository vItemRepository;

    @Override
    public VItemDetail store(VItemDetail vItemDetail) {
        return vItemRepository.save(vItemDetail);
    }

    @Override
    public String delete(VItemDetail vItemDetail) {
        vItemRepository.delete(vItemDetail);
        return vItemDetail.getId().toString();
    }
}
