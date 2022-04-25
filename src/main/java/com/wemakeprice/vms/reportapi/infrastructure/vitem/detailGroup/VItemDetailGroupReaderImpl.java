package com.wemakeprice.vms.reportapi.infrastructure.vitem.detailGroup;


import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupReader;
import com.wemakeprice.vms.reportapi.infrastructure.vitem.detail.VItemDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VItemDetailGroupReaderImpl implements VItemDetailGroupReader {

    private final VItemDetailGroupRepository vItemDetailGroupRepository;
    private final VItemDetailRepository vItemDetailRepository;

    @Override
    public VItemInfo.VItemDetailInfo getVItemBy(Long vItemDetailGroupId) {
        return null;
    }

    @Override
    public List<VItemInfo.VItemDetailInfo> getVItemDetailSeries(VItemDetailGroup vItemDetailGroup) {
        return null;
    }
}
