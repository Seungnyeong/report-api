package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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
    public List<VItemInfo.VItemDetailGroupInfo>  getVItemDetailGroupSeries(VItem vItem) {
        var vItemDetailGroupList = vItem.getVItemDetailGroupList();
        return vItemDetailGroupList.stream()
                .map(vItemDetailGroup -> {
                    var vItemDetailList = vItemDetailGroup.getVItemDetailsList();
                    var vItemDetailInfoList = vItemDetailList.stream()
                            .map(VItemInfo.VItemDetailInfo::new)
                            .collect(Collectors.toList());
                    return new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup, vItemDetailInfoList);
                }).collect(Collectors.toList());
    }

    @Override
    public VItem getOne(Long vItemId) {
        return vItemRepository.getOne(vItemId);
    }

    @Override
    public List<VItem> getVItemList() {
        return vItemRepository.findAll();
    }
}
