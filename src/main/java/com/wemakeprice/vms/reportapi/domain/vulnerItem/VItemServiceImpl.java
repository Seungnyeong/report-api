package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemServiceImpl implements VItemService{
    private final VItemStore vItemStore;
    private final VItemDetailSeriesFactory vItemDetailSeriesFactory;

    @Transactional
    @Override
    public String registerVItem(VItemCommand.RegisterVItemRequest request) {
        var initItem = request.toEntity();
        var item = vItemStore.store(initItem);
        vItemDetailSeriesFactory.store(request, item);
        return item.getVDetail();
    }
}