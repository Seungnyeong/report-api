package com.wemakeprice.vms.reportapi.domain.vitem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VItemServiceImpl implements VItemService{
    private final VItemStore vItemStore;
    private final VItemDetailStore vItemDetailStore;
    private final VItemDetailSeriesFactory vItemDetailSeriesFactory;
    private final VItemReader vItemReader;
    private final VItemDetailReader vItemDetailReader;

    /*
    *  목록과 함께 대응방법 저장
    * */
    @Transactional
    @Override
    public VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest command) {
        var initItem = command.toEntity();
        var vItem = vItemStore.store(initItem);
        vItemDetailSeriesFactory.store(command, vItem);
        var vItemDetailList = vItemReader.getVItemDetail(vItem);
        return new VItemInfo.Main(vItem, vItemDetailList);
    }

    /*
    *   세부 진단 항목 조회
    * */
    @Transactional
    @Override
    public VItemInfo.Main retrieveVItem(Long vItemId) {
        var vItem = vItemReader.getVItemBy(vItemId);
        var vItemDetailList = vItemReader.getVItemDetail(vItem);
        return new VItemInfo.Main(vItem, vItemDetailList);
    }

    @Override
    public List<VItemInfo.Main> retrieveVItemList() {
        var vItemList = vItemReader.getVItemList();
        return vItemList.stream().map(vItem -> {
            var vItemDetailList = vItemReader.getVItemDetail(vItem);
            return new VItemInfo.Main(vItem, vItemDetailList);
        }).collect(Collectors.toList());
    }

    /*
    *   해당 목록에 대응방법만 추가 저장
    * */
    @Transactional
    @Override
    public VItemInfo.VItemDetailInfo registerVItemDetail(VItemCommand.RegisterVItemDetailRequest command, Long vItemId) {
        var vItem = vItemReader.getVItemBy(vItemId);
        var initVItemDetail = command.toEntity(vItem);
        var vItemDetail = vItemDetailStore.store(initVItemDetail);
        return new VItemInfo.VItemDetailInfo(vItemDetail);
    }

    @Transactional
    @Override
    public String deleteVItem(Long vItemId) {
        var vItem = vItemReader.getVItemBy(vItemId);
        return vItemStore.delete(vItem);
    }

    @Transactional
    @Override
    public String deleteVItemDetail(Long vItemDetailId) {
        var vItemDetail = vItemDetailReader.getVItemDetail(vItemDetailId);
        return vItemDetailStore.delete(vItemDetail);
    }

    @Transactional
    @Override
    public VItemInfo.Main updateVItem(VItemCommand.UpdateVItemRequest command) {
        var vItem = vItemReader.getOne(command.getId());
        vItem.updateVItem(command);
        var vItemDetailList = vItemReader.getVItemDetail(vItem);
        return new VItemInfo.Main(vItem, vItemDetailList);
    }

    @Transactional
    @Override
    public VItemInfo.VItemDetailInfo updateVItemDetail(VItemCommand.UpdateVItemDetailRequest command) {
        var vItemDetail = vItemDetailReader.getVItemDetail(command.getId());
        vItemDetail.updateDetail(command);
        return new VItemInfo.VItemDetailInfo(vItemDetail);
    }
}