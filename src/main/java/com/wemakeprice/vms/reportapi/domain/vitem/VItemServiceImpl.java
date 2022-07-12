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
    private final VItemSeriesFactory vItemDetailSeriesFactory;
    private final VItemReader vItemReader;

    /*
    *  목록과 함께 대응방법 저장
    * */
    @Transactional
    @Override
    public VItemInfo.Main registerVItem(VItemCommand.RegisterVItemRequest command) {
        var initItem = command.toEntity();
        var vItem = vItemStore.store(initItem);
        var vItemDetailGroupList = vItemDetailSeriesFactory.store(command, vItem);
        return new VItemInfo.Main(vItem,vItemDetailGroupList);
    }

    @Transactional
    @Override
    public VItemInfo.Main retrieveVItem(Long vItemId) {
        var vItem = vItemReader.getVItemBy(vItemId);
        var vItemDetailGroupList = vItemReader.getVItemDetailGroupSeries(vItem);
        return new VItemInfo.Main(vItem, vItemDetailGroupList);
    }

    @Transactional
    @Override
    public List<VItemInfo.Main> retrieveVItemList() {
        var vItemList = vItemReader.getVItemList();
        return vItemList.stream().map(vItem -> {
            var vItemDetailList = vItemReader.getVItemDetailGroupSeries(vItem);
            return new VItemInfo.Main(vItem, vItemDetailList);
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String deleteVItem(Long vItemId) {
        var vItem = vItemReader.getVItemBy(vItemId);
        return vItemStore.delete(vItem);
    }

    @Transactional
    @Override
    public VItemInfo.Main updateVItem(VItemCommand.UpdateVItemRequest command) {
        var vItem = vItemReader.getOne(command.getId());
        vItem.updateVItem(command);
        var vItemDetailList = vItemReader.getVItemDetailGroupSeries(vItem);
        return new VItemInfo.Main(vItem, vItemDetailList);
    }
}