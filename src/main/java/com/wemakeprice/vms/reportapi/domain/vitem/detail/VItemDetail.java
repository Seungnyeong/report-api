package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

@Entity
@Table( name = "v_item_detail")
@Getter
@NoArgsConstructor
@Slf4j
public class VItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 1000)
    private String vDetail;

    @ManyToOne
    @JoinColumn(name = "v_item_id")
    private VItem vItem;

    @Builder
    public VItemDetail(VItem vItem, String vDetail) {
        this.vItem = vItem;
        this.vDetail = vDetail;
    }

    public void updateDetail(VItemCommand.UpdateVItemDetailRequest request) {
        if(!StringUtils.isEmpty(request.getDetail())) this.vDetail = request.getDetail();
    }
}
