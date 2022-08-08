package com.wemakeprice.vms.reportapi.domain.report.image;

import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.docx4j.org.apache.poi.util.StringUtil;

import javax.persistence.*;
import java.nio.file.Path;

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
    @JoinColumn(name = "report_option_id")
    private ReportOption reportOption;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @Builder
    public ReportOptionImage(Report report, ReportOption reportOption,
                             Path reportOptionImageFilePath, String imageName, String extension,
                             String caption, String description, Integer ordering
    ) {
        this.report = report;
        this.reportOption = reportOption;
        this.reportOptionImageFilePath = reportOptionImageFilePath.toString();
        this.imageName = imageName;
        this.extension = extension;
        this.caption = caption;
        this.description = description;
        this.ordering = ordering;
    }
}
