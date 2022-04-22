package com.wemakeprice.vms.reportapi.infrastructure.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.*;
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
public class VItemDetailSeriesFactoryImpl implements VItemDetailSeriesFactory {

    private final VItemDetailStore vItemDetailStore;

    @Override
    public List<VItemDetail> store(VItemCommand.RegisterVItemRequest request, VItem item) {
        var vItemDetailRequestList  = request.getVItemDetailRequestList();
        if (CollectionUtils.isEmpty(vItemDetailRequestList)) return Collections.emptyList();
        return vItemDetailRequestList.stream()
                .map(detail -> {
                    var initViewDetail = detail.toEntity(item);
                    vItemDetailStore.store(initViewDetail);
                    return initViewDetail;
                }).collect(Collectors.toList());
    }
}
