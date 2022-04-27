package com.wemakeprice.vms.reportapi.domain.vitem;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Table(name = "v_list")
@Entity
@Getter
@NoArgsConstructor
public class VItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String vCategoryName;

    @Column(length = 3)
    private Integer vCategoryCode;


    @Column(length = 1000)
    private String vDetail;


    @Column(length = 10)
    private String caseTag;

    @Column(length = 10)
    private String respondTag;

    private Integer ordering;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vItem" , cascade = CascadeType.ALL)
    private List<VItemDetailGroup> vItemDetailGroupList = Lists.newArrayList();

    private Long annualTableId;


    @Builder
    public VItem(String vCategoryName,
                 Integer vCategoryCode,
                 String vDetail,
                 String caseTag,
                 String respondTag,
                 Integer ordering) {
        this.vCategoryName = vCategoryName;
        this.vCategoryCode = vCategoryCode;
        this.vDetail = vDetail;
        this.caseTag = caseTag;
        this.respondTag = respondTag;
        this.ordering = 1;
    }

    public void updateVItem(VItemCommand.UpdateVItemRequest command) {
        if (!StringUtils.isEmpty(command.getVCategoryName())) this.vCategoryName = command.getVCategoryName();
        if (command.getVCategoryCode() != null) this.vCategoryCode = command.getVCategoryCode();
        if (command.getOrdering() != null) this.ordering = command.getOrdering();
        if (!StringUtils.isEmpty(command.getCaseTag())) this.caseTag = command.getCaseTag();
        if (!StringUtils.isEmpty(command.getRespondTag())) this.respondTag = command.getRespondTag();
        if (!StringUtils.isEmpty(command.getVDetail())) this.vDetail = command.getVDetail();
    }
}
