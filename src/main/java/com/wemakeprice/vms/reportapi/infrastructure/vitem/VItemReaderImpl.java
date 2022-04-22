package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class VItemReaderImpl implements VItemReader {

    private final VItemRepository vItemRepository;

    @Override
    public VItem getVItemBy(Long vItemId) {
        return vItemRepository.findById(vItemId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<VItemInfo.VItemDetailInfo> getVItemDetail(VItem vItem) {
        var vItemDetailList = vItem.getVItemDetailsList();

        return vItemDetailList.stream()
                .map(VItemInfo.VItemDetailInfo::new).collect(Collectors.toList());
    }

    @Override
    public List<VItem> getVItemList() {
        return vItemRepository.findAll();
    }
}
