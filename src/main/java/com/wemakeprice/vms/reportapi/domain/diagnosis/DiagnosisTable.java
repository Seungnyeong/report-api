package com.wemakeprice.vms.reportapi.domain.diagnosis;

import com.wemakeprice.vms.reportapi.domain.users.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getServiceTeam() {
        return serviceTeam;
    }

    public void setServiceTeam(String serviceTeam) {
        this.serviceTeam = serviceTeam;
    }

    public String getServicePart() {
        return servicePart;
    }

    public void setServicePart(String servicePart) {
        this.servicePart = servicePart;
    }

    public String getServiceManager() {
        return serviceManager;
    }

    public void setServiceManager(String serviceManager) {
        this.serviceManager = serviceManager;
    }

    public String getCmUrl() {
        return cmUrl;
    }

    public void setCmUrl(String cmUrl) {
        this.cmUrl = cmUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJiraName() {
        return jiraName;
    }

    public void setJiraName(String jiraName) {
        this.jiraName = jiraName;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getJiraTicket() {
        return jiraTicket;
    }

    public void setJiraTicket(String jiraTicket) {
        this.jiraTicket = jiraTicket;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getImprovementCritical() {
        return improvementCritical;
    }

    public void setImprovementCritical(String improvementCritical) {
        this.improvementCritical = improvementCritical;
    }

    public String getImprovementImportant() {
        return improvementImportant;
    }

    public void setImprovementImportant(String improvementImportant) {
        this.improvementImportant = improvementImportant;
    }

    public String getImprovementNormal() {
        return improvementNormal;
    }

    public void setImprovementNormal(String improvementNormal) {
        this.improvementNormal = improvementNormal;
    }

    public String getEnvInfoText() {
        return envInfoText;
    }

    public void setEnvInfoText(String envInfoText) {
        this.envInfoText = envInfoText;
    }

    public String getSonarUrl() {
        return sonarUrl;
    }

    public void setSonarUrl(String sonarUrl) {
        this.sonarUrl = sonarUrl;
    }

    public String getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(String certificationType) {
        this.certificationType = certificationType;
    }

}