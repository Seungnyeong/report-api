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

    @Transactional
    @Override
    public String  registerVItem(VItemCommand.RegisterVItemRequest request) {
        var initItem = VItem.builder()
                .vCategoryName(request.getVCategoryName())
                .vSubCategoryName(request.getVSubCategoryName())
                .vDetail(request.getVDetail())
                .build();
        var item = vItemStore.store(initItem);
        return item.getVDetail();
    }
}