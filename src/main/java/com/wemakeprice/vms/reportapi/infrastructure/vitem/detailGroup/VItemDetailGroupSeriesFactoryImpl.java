package com.wemakeprice.vms.reportapi.infrastructure.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupSeriesFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class VItemDetailGroupSeriesFactoryImpl implements VItemDetailGroupSeriesFactory {
    @Override
    public List<VItemDetail> store(VItemCommand.RegisterVItemGroupRequest request, VItemDetailGroup vItemDetailGroup) {
        return null;
    }
}
