package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;


import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import java.util.List;

public interface VItemDetailGroupReader {
    VItemDetailGroup getVItemBy(Long vItemDetailGroupId);
    List<VItemInfo.VItemDetailInfo> getVItemDetailSeries(VItemDetailGroup vItemDetailGroup);
    List<VItemDetailGroup> getVItemDetailGroupByVItem(VItem vItem);
}
