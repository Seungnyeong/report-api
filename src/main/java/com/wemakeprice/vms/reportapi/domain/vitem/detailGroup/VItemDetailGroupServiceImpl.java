package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemDetailGroupServiceImpl implements VItemDetailGroupService {

    private final VItemDetailGroupReader vItemDetailGroupReader;
    private final VItemDetailGroupStore vItemDetailGroupStore;
    private final VItemDetailGroupSeriesFactory vItemDetailGroupSeriesFactory;
    private final VItemReader vItemReader;

    @Transactional
    @Override
    public VItemInfo.VItemDetailGroupInfo registerVItemGroup(VItemCommand.RegisterVItemGroupRequest command, Long vItemId) {
        var initVItem = vItemReader.getVItemBy(vItemId);
        var initVItemDetailGroup= command.toEntity(initVItem);
        var vItemDetailGroup = vItemDetailGroupStore.store(initVItemDetailGroup);
        var vItemDetailInfoList = vItemDetailGroupSeriesFactory.store(command, vItemDetailGroup);
        return new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup, vItemDetailInfoList);
    }

    @Transactional
    @Override
    public String deleteVItemGroup(Long vItemDetailGroupId) {
        var vItemDetailGroup = vItemDetailGroupReader.getVItemBy(vItemDetailGroupId);
        return vItemDetailGroupStore.delete(vItemDetailGroup);
    }

    @Transactional
    @Override
    public void changeGradeToHigh(Long vItemDetailGroupId) {
        var vItemDetailGroup = vItemDetailGroupReader.getVItemBy(vItemDetailGroupId);
        vItemDetailGroup.changeGradeToHigh();
    }

    @Transactional
    @Override
    public VItemInfo.VItemDetailGroupInfo updateVItemGroup(VItemDetailGroupCommand.UpdateVItemDetailGroupRequest command) {
        var vItemDetailGroup = vItemDetailGroupReader.getVItemBy(command.getId());
        vItemDetailGroup.updateDetailGroup(command);
        var vItemDetailInfoList = vItemDetailGroupReader.getVItemDetailSeries(vItemDetailGroup);
        return new VItemInfo.VItemDetailGroupInfo(vItemDetailGroup, vItemDetailInfoList);
    }

    @Transactional
    @Override
    public void changeGradeToMedium(Long vItemDetailGroupId) {
        var vItemDetailGroup = vItemDetailGroupReader.getVItemBy(vItemDetailGroupId);
        vItemDetailGroup.changeGradeToMedium();
    }

    @Transactional
    @Override
    public void changeGradeToLow(Long vItemDetailGroupId) {
        var vItemDetailGroup = vItemDetailGroupReader.getVItemBy(vItemDetailGroupId);
        vItemDetailGroup.changeGradeToLow();
    }

    @Transactional
    @Override
    public List<VItemInfo.VItemDetailGroupInfo> retrieveDetailGroupByVItemId(Long vItemId) {
        return null;
    }
}
