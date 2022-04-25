package com.wemakeprice.vms.reportapi.infrastructure.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class VItemDetailGroupStoreImpl implements VItemDetailGroupStore {

    private final VItemDetailGroupRepository vItemDetailGroupRepository;

    @Override
    public VItemDetailGroup store(VItemDetailGroup vItemDetailGroup) {
        return vItemDetailGroupRepository.save(vItemDetailGroup);
    }

    @Override
    public String delete(VItemDetailGroup vItemDetailGroup) {
        vItemDetailGroupRepository.delete(vItemDetailGroup);
        return vItemDetailGroup.getId().toString();
    }
}
