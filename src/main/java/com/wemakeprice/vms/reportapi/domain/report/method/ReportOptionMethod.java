package com.wemakeprice.vms.reportapi.domain.report.method;

import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;


@Entity
@Slf4j
@Getter
@NoArgsConstructor
@Table(name = "report_option_method")
public class ReportOptionMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 200)
    private String methodName;

    @Column(length = 1000)
    private String methodPackage;

    @Column(length = 2000)
    private String methodDescription;

    private Integer ordering;

    @ManyToOne
    @JoinColumn(name = "report_option_id")
    private ReportOption reportOption;

    @Builder
    public ReportOptionMethod(
            String methodName,
            String methodPackage,
            String methodDescription,
            Integer ordering,
            ReportOption reportOption
    ) {
        this.methodName = methodName;
        this.methodPackage = methodPackage;
        this.methodDescription = methodDescription;
        this.ordering = ordering;
        this.reportOption = reportOption;
    }

}
