package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Table( name = "v_item_detail")
@Getter
@Slf4j
public class VItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 1000)
    private String vDetail;
}
