package com.wemakeprice.vms.reportapi.application.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemDetailFacade {

    private final VItemDetailService vItemDetailService;

    public VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest command, Long vItemGroupDetailId) {
        return vItemDetailService.registerVItemDetail(command,vItemGroupDetailId);
    }

    public String deleteVItemDetail(Long vItemDetailId) {
        return vItemDetailService.deleteVItemDetail(vItemDetailId);
    }

    public List<VItemInfo.VItemDetailInfo> retrieveDetailInfo(Long vItemGroupDetailId) {
        return vItemDetailService.retrieveAllDetail(vItemGroupDetailId);
    }

    public VItemInfo.VItemDetailInfo updateVItemDetail(VItemCommand.UpdateVItemDetailRequest request ) {
        return vItemDetailService.updateVItemDetail(request);
    }
}
