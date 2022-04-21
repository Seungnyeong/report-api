package com.wemakeprice.vms.reportapi.domain.report.optionGroup;


import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

//@Slf4j
//@Table(name = "report_option_group")
//@Entity
//@Getter
public class ReportOptionGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @ManyToOne
//    @JoinColumn(name = "report_id")
//    private Report report;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reportOptionGroup", cascade = CascadeType.PERSIST)
//    private List<ReportOption> reportOptionsList = Lists.newArrayList();

    private Integer ordering;

    @Column(length = 100)
    private String reportOptionGroupName;

    private OptionGrade reportOptionGrade;

    @Getter
    @RequiredArgsConstructor
    public enum OptionGrade {
        HIGH("상"),
        MEDIUM("중"),
        LOW("하");

        private final String description;
    }
}
