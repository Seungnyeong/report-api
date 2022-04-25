package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemDetailGroupServiceImpl implements VItemDetailGroupService {

    private final VItemDetailGroupReader vItemDetailGroupReader;
    private final VItemDetailGroupStore vItemDetailGroupStore;
    private final VItemDetailGroupSeriesFactory vItemDetailGroupSeriesFactory;

    @Override
    public VItemInfo.VItemDetailGroupInfo registerVItemGroup(VItemCommand.RegisterVItemGroupRequest command, Long vItemId) {

        return null;
    }
}
