package com.wemakeprice.vms.reportapi.infrastructure.vitem.detailGroup;


import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupReader;
import com.wemakeprice.vms.reportapi.infrastructure.vitem.detail.VItemDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class VItemDetailGroupReaderImpl implements VItemDetailGroupReader {

    private final VItemDetailGroupRepository vItemDetailGroupRepository;
    private final VItemDetailRepository vItemDetailRepository;

    @Override
    public VItemDetailGroup getVItemBy(Long vItemDetailGroupId) {
        return vItemDetailGroupRepository.findVItemDetailGroupById(vItemDetailGroupId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<VItemInfo.VItemDetailInfo> getVItemDetailSeries(VItemDetailGroup vItemDetailGroup) {
        return null;
    }

    @Override
    public List<VItemDetailGroup> getVItemDetailGroupByVItem(VItem vItem) {
        return vItem.getVItemDetailGroupList();
    }
}
