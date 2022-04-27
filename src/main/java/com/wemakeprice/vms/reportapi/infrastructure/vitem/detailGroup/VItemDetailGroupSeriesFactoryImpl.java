package com.wemakeprice.vms.reportapi.infrastructure.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetailStore;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupSeriesFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class VItemDetailGroupSeriesFactoryImpl implements VItemDetailGroupSeriesFactory {
    private final VItemDetailStore vItemDetailStore;

    @Override
    public List<VItemInfo.VItemDetailInfo> store(VItemCommand.RegisterVItemGroupRequest command, VItemDetailGroup vItemDetailGroup) {
        var vItemDetailList= command.getVItemDetailRequestList();
        if (CollectionUtils.isEmpty(vItemDetailList)) return Collections.emptyList();

        return vItemDetailList.stream().map(vItemDetail -> {
            var initVItemDetail = vItemDetail.toEntity(vItemDetailGroup);
            var resultItemDetail = vItemDetailStore.store(initVItemDetail);
            return new VItemInfo.VItemDetailInfo(resultItemDetail);
        }).collect(Collectors.toList());
    }
}