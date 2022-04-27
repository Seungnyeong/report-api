package com.wemakeprice.vms.reportapi.infrastructure.vitem.detail;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetailReader;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class VItemDetailReaderImpl implements VItemDetailReader {

    private final VItemDetailRepository vItemDetailRepository;

    @Override
    public VItemDetail getVItemDetail(Long vItemDetailId) {
        return vItemDetailRepository.findVItemDetailById(vItemDetailId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<VItemDetail> getAllDetailsGroupBy(VItemDetailGroup vItemDetailGroup) {
        return vItemDetailGroup.getVItemDetailsList();
    }
}
