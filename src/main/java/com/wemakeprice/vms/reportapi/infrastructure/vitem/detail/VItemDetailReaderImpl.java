package com.wemakeprice.vms.reportapi.infrastructure.vitem.detail;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemDetail;
import com.wemakeprice.vms.reportapi.domain.vulnerItem.VItemDetailReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class VItemDetailReaderImpl implements VItemDetailReader {

    private final VItemDetailRepository vItemDetailRepository;

    @Override
    public VItemDetail getVItemDetail(Long vItemDetailId) {
        return vItemDetailRepository.findVItemDetailById(vItemDetailId).orElseThrow(EntityNotFoundException::new);
    }
}
