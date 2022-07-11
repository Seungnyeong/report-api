package com.wemakeprice.vms.reportapi.domain.diagnosis;

import com.wemakeprice.vms.reportapi.domain.users.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Table(name = "diagnosis_table")
public class DiagnosisTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "year", length = 45)
    private String year;

    @Column(name = "classify", length = 45)
    private String classify;

    @Column(name = "service_type", length = 45)
    private String serviceType;

    @Column(name = "service_name")
    private String serviceName;

    @Column(name = "service_url")
    private String serviceUrl;

    @Column(name = "service_team", length = 45)
    private String serviceTeam;

    @Column(name = "service_part", length = 45)
    private String servicePart;

    @Column(name = "service_manager", length = 45)
    private String serviceManager;

    @Column(name = "cm_url")
    private String cmUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "status", length = 45)
    private String status;

    @Column(name = "result", length = 45)
    private String result;

    @Column(name = "jira_name", length = 45)
    private String jiraName;

    @Lob
    @Column(name = "discription")
    private String discription;

    @Column(name = "step", length = 45)
    private String step;

    @Column(name = "stage", length = 45)
    private String stage;

    @Column(name = "jira_ticket")
    private String jiraTicket;

    @Column(name = "final_grade", length = 45)
    private String finalGrade;

    @Column(name = "improvement_critical", length = 45)
    private String improvementCritical;

    @Column(name = "improvement_important", length = 45)
    private String improvementImportant;

    @Column(name = "improvement_normal", length = 45)
    private String improvementNormal;

    @Lob
    @Column(name = "env_info_text")
    private String envInfoText;

    @Column(name = "sonar_url", length = 300)
    private String sonarUrl;

    @Column(name = "certification_type", length = 3)
    private String certificationType;

}