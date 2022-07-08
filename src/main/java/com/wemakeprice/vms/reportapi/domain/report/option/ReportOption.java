package com.wemakeprice.vms.reportapi.domain.report.option;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.report.Image.ReportOptionImage;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Entity
@Table(name = "report_option")
@NoArgsConstructor
@Getter
public class ReportOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String vName;

    @Column(length = 3)
    private Integer reportVCount;

    @Column(length = 1000)
    private String reportVIssue;

    @Column(length = 1000)
    private String reportVResponse;

    @ManyToOne
    @JoinColumn(name = "report_option_group_id")
    private ReportOptionGroup reportOptionGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reportOption" , cascade = CascadeType.PERSIST)
    private List<ReportOptionMethod> reportOptionMethodList = Lists.newArrayList();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reportOption" , cascade = CascadeType.PERSIST)
    private List<ReportOptionImage> reportOptionImageList = Lists.newArrayList();

    private Integer ordering;
}
