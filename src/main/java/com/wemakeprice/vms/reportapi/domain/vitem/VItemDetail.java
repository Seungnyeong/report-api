package com.wemakeprice.vms.reportapi.domain.vitem;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

}
