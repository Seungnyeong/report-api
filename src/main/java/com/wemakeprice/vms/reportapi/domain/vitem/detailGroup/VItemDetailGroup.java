package com.wemakeprice.vms.reportapi.domain.vitem.detailGroup;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.common.exception.InvalidParamException;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.detail.VItemDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ("v_item_detail_group"))
@Getter
@NoArgsConstructor
@Slf4j
public class VItemDetailGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private VGroupGrade vGroupGrade;
    private String vGroupName;
    private Integer vGroupCode;
    private Integer ordering;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vItemDetailGroup" , cascade = CascadeType.ALL)
    private final List<VItemDetail> vItemDetailsList = Lists.newArrayList();

    @ManyToOne
    @JoinColumn(name = "v_item_id")
    private VItem vItem;

    @Getter
    @RequiredArgsConstructor
    public enum VGroupGrade {
        HIGH("상"),
        MEDIUM("중"),
        LOW("하");

        private final String description;
    }

    @Builder
    public VItemDetailGroup(VItem vItem,
                            String vGroupName,
                            Integer vGroupCode,
                            Integer ordering
                            ) {
        this.vItem = vItem;
        this.vGroupGrade = VGroupGrade.LOW;
        this.vGroupName = vGroupName;
        this.vGroupCode = vGroupCode;
        this.ordering = ordering;
    }


    public void changeGradeToHigh () {
        if (this.vGroupGrade == VGroupGrade.HIGH) throw new InvalidParamException("LOW or MEDIUM 으로 변경하세요");
        this.vGroupGrade = VGroupGrade.HIGH;
    }

    public void changeGradeToLow() {
        if (this.vGroupGrade == VGroupGrade.LOW) throw new InvalidParamException("HIGH or MEDIUM 으로 변경하세요");
        this.vGroupGrade = VGroupGrade.LOW;
    }

    public void changeGradeToMedium() {
        if (this.vGroupGrade == VGroupGrade.MEDIUM) throw new InvalidParamException("LOW or HIGH 으로 변경하세요");
        this.vGroupGrade = VGroupGrade.MEDIUM;
    }

    public void updateDetailGroup(VItemDetailGroupCommand.UpdateVItemDetailGroupRequest command) {
        if(!StringUtils.isEmpty(command.getVGroupName())) this.vGroupName = command.getVGroupName();
        if(command.getVGroupCode() != null) this.vGroupCode = command.getVGroupCode();
        if(command.getOrdering() != null) this.ordering = command.getOrdering();
    }
}
