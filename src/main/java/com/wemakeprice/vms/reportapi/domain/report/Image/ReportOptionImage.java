package com.wemakeprice.vms.reportapi.domain.report.Image;

import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Slf4j
@Getter
@NoArgsConstructor
@Table(name = "report_option_images")
public class ReportOptionImage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 1000)
    private String reportOptionImageFilePath;

    @Column(length = 100)
    private String imageName;

    @Column(length = 5)
    private String extension;
    private Integer ordering;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String caption;

    @ManyToOne
    @JoinColumn(name = "report_option_id", insertable = false, updatable = false)
    private ReportOption reportOptionId;

    @ManyToOne
    @JoinColumn(name = "report_option_id", insertable = false, updatable = false)
    private ReportOption reportOption;

    public void setReportOption(ReportOption reportOption) {
        this.reportOption = reportOption;
    }
}
