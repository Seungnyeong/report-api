package com.wemakeprice.vms.reportapi.domain.report;


import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Entity
//@NoArgsConstructor
//@Table(name = "reports")
//@Getter
public class Report extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private Long userId;

    @Column(nullable = false, length = 20)
    private String reportControlNumber;

    @Column(nullable = false, length = 20)
    private String jiraTicketName;

    @Enumerated(EnumType.STRING)
    private Vulnerability reportVPossibility;

    @Enumerated(EnumType.STRING)
    private Grade reportVGrade;

    @Column(length = 1000)
    private String reportFilePath;

    @Column(length = 100)
    private String reportPassword;

    @Column(length = 1000 , nullable = false)
    private String fileExtension;

    @Column(length = 10 )
    private int reportVersion;

    @Column(length = 1000)
    private String reportFileName;

    @Column(nullable = false)
    private Long tableId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report" , cascade = CascadeType.PERSIST)
    private List<ReportOptionGroup> reportOptionGroupsList = Lists.newArrayList();

    @Builder
    public Report(Long tableId, Long userId, String jiraTicketName, String reportFileName) {
        this.userId = userId;
        this.tableId = tableId;
        this.reportVersion = 1;
        this.jiraTicketName = jiraTicketName;
        this.reportFileName = reportFileName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Vulnerability {
        HIGH("어려움"),
        MEDIUM("중간"),
        LOW("쉬움");

        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Grade {
        FIRST("1급"),
        SECOND("2급"),
        THIRD("3급");

        private final String description;
    }
}
