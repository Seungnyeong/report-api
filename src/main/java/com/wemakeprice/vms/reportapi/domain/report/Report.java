package com.wemakeprice.vms.reportapi.domain.report;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.common.exception.AuthorizationException;
import com.wemakeprice.vms.reportapi.common.exception.InvalidParamException;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import com.wemakeprice.vms.reportapi.domain.users.User;
import lombok.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.wemakeprice.vms.reportapi.common.utils.common.ReportCommon.getTempPassword;

@Entity
@NoArgsConstructor
@Table(name = "reports")
@Getter
public class Report extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @Column(nullable = false, length = 20, unique = true)
    private String reportControlNumber;

    @Column(nullable = false, length = 20, unique = true)
    private String jiraTicketNumber;

    @Enumerated(EnumType.STRING)
    private Vulnerability reportVPossibility;

    @Enumerated(EnumType.STRING)
    private Grade reportVGrade;

    @Column(length = 1000, nullable = true)
    private String reportFilePath;

    @Column(length = 100)
    private String reportPassword;

    @Column(length = 5, nullable = true)
    private String fileExtension;

    @Column(length = 10 )
    private int reportVersion;

    @Column(length = 1000 , nullable = true)
    private String reportFileName;

    @Column(length = 1000)
    private String generalReview;

    @OneToOne
    @JoinColumn(name = "diagnosis_table_id", referencedColumnName = "id")
    private DiagnosisTable diagnosisTable;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report" , cascade = CascadeType.PERSIST)
    private List<ReportOptionGroup> reportOptionGroupsList = Lists.newArrayList();

    @Builder
    public Report(DiagnosisTable diagnosisTable,
                  String jiraTicketNumber,
                  String reportFileName,
                  String generalReview,
                  String title,
                  Report.Vulnerability reportVPossibility,
                  Report.Grade reportVGrade
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) throw new AuthorizationException();
        if (diagnosisTable == null) throw new InvalidParamException("진단 테이블은 필수 값입니다.");
        if (reportVPossibility == null) throw new InvalidParamException("침해 가능성을 선택해 주세요.");
        if (reportVGrade == null) throw new InvalidParamException("취약점 등급을 선택해주세요");
        if (jiraTicketNumber == null) throw new InvalidParamException("지라 티켓이 없습니다.");

        this.title = title;
        this.diagnosisTable = diagnosisTable;
        this.reportVersion = 1;
        this.jiraTicketNumber = jiraTicketNumber;
        this.reportFileName = reportFileName;
        this.reportControlNumber = generateControlNumber(jiraTicketNumber);
        this.reportPassword = getTempPassword(10);
        this.generalReview = generalReview;
        this.jiraTicketNumber = jiraTicketNumber;
        this.reportVPossibility = reportVPossibility;
        this.reportVGrade = reportVGrade;
        this.user = user;

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

    private static String generateControlNumber(String jiraTicketNumber) {
        int index = jiraTicketNumber.indexOf('-');
        int size = jiraTicketNumber.length();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar dateTime = Calendar.getInstance();
        String year = sdf.format(dateTime.getTime());
        String numericJiraNum = jiraTicketNumber.substring(index, size);
        return year + numericJiraNum;
    }

    public void updateFilePath(String reportFilePath) {
        if(!StringUtils.isEmpty(reportFilePath)) {
            this.fileExtension = FilenameUtils.getExtension(reportFilePath);
            this.reportFilePath = reportFilePath;
            this.reportFileName = FilenameUtils.getName(reportFilePath);
        }
    }
}
