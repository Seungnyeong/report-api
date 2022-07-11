package com.wemakeprice.vms.reportapi.domain.report.optionGroup;


import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Table(name = "report_option_group")
@Entity
@NoArgsConstructor
@Getter
public class ReportOptionGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reportOptionGroup", cascade = CascadeType.PERSIST)
    private List<ReportOption> reportOptionsList = Lists.newArrayList();

    private Integer ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_item_detail_group_id")
    private VItemDetailGroup vItemDetailGroup;

    @Builder
    public ReportOptionGroup(Report report, VItemDetailGroup vItemDetailGroup) {
        this.report = report;
        this.vItemDetailGroup = vItemDetailGroup;
    }
}
