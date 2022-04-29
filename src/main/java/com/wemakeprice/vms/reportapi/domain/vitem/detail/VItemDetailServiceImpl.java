package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroupReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemDetailServiceImpl implements VItemDetailService {

    private final VItemDetailStore vItemDetailStore;
    private final VItemDetailReader vItemDetailReader;
    private final VItemDetailGroupReader vItemDetailGroupReader;

    @Transactional
    @Override
    public VItemInfo.VItemDetailInfo updateVItemDetail(VItemCommand.UpdateVItemDetailRequest command) {
        var vItemDetail = vItemDetailReader.getVItemDetail(command.getId());
        vItemDetail.updateDetail(command);
        return new VItemInfo.VItemDetailInfo(vItemDetail);
    }

    @Transactional
    @Override
    public VItemInfo.VItemDetailInfo removeVItemDetailRespond(Long vItemDetailId, int index) {
        var vItemDetail = vItemDetailReader.getVItemDetail(vItemDetailId);
        vItemDetail.removeResponse(index);
        return new VItemInfo.VItemDetailInfo(vItemDetail);
    }

    @Transactional
    @Override
    public VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest command, Long vItemDetailGroupId) {
        var vItemGroupDetail = vItemDetailGroupReader.getVItemBy(vItemDetailGroupId);
        var initVItemDetail = command.toEntity(vItemGroupDetail);
        var vItemDetail = vItemDetailStore.store(initVItemDetail);
        return new VItemInfo.VItemDetailInfo(vItemDetail);
    }

    @Transactional
    @Override
    public String deleteVItemDetail(Long vItemDetailId) {
        var vItemDetail = vItemDetailReader.getVItemDetail(vItemDetailId);
        return vItemDetailStore.delete(vItemDetail);
    }

    @Transactional
    @Override
    public List<VItemInfo.VItemDetailInfo> retrieveAllDetail(Long vItemDetailId) {
        var vItemDetailGroup = vItemDetailGroupReader.getVItemBy(vItemDetailId);
        var vItemDetailList  = vItemDetailReader.getAllDetailsGroupBy(vItemDetailGroup);
        return vItemDetailList.stream().map(VItemInfo.VItemDetailInfo::new).collect(Collectors.toList());
    }
}
