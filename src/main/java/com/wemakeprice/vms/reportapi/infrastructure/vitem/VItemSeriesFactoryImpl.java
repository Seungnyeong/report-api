package com.wemakeprice.vms.reportapi.infrastructure.vitem;

import com.wemakeprice.vms.reportapi.domain.vitem.*;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemSeriesFactory;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetailStore;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class VItemSeriesFactoryImpl implements VItemSeriesFactory {

    private final VItemDetailGroupStore vItemDetailGroupStore;
    private final VItemDetailStore vItemDetailStore;

    @Override
    public List<VItemInfo.VItemDetailGroupInfo> store(VItemCommand.RegisterVItemRequest command, VItem vItem) {
        var vItemOptionGroupRequestList = command.getVItemGroupRequestList();
        if (CollectionUtils.isEmpty(vItemOptionGroupRequestList)) return Collections.emptyList();
        return vItemOptionGroupRequestList.stream()
                .map(requestVItemDetailGroup -> {
                    var initVItemDetailGroup = requestVItemDetailGroup.toEntity(vItem);
                    var vItemDetailGroup = vItemDetailGroupStore.store(initVItemDetailGroup);

                    var vItemDetailInfoList = requestVItemDetailGroup.getVItemDetailRequestList().stream().map(requestVItemDetail -> {
                        var initVItemDetail = requestVItemDetail.toEntity(vItemDetailGroup);
                        vItemDetailStore.store(initVItemDetail);
                        return new VItemInfo.VItemDetailInfo(initVItemDetail);
                    }).collect(Collectors.toList());
                    return new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup, vItemDetailInfoList);
                }).collect(Collectors.toList());
    }
}
