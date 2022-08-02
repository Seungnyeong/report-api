package com.wemakeprice.vms.reportapi.domain.report;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import com.wemakeprice.vms.reportapi.domain.users.User;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

public class ReportInfo  {

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Main {
        private final Long id;
        private final String title;
        private final User user;
        private final String reportControlNumber;
        private final String jiraTicketNumber;
        private final Report.Vulnerability reportVPossibility;
        private final Report.Grade reportVGrade;
        private final String reportPassword;
        private final String generalReview;
        private final DiagnosisTable diagnosisTable;
        private final String fileExtension;
        private final String reportFilePath;
        private final List<ReportOptionGroupInfo> reportOptionGroupsList;
        private final LocalDateTime created;

        public Main(Report report, List<ReportOptionGroupInfo> reportOptionGroupInfoList) {
            this.id = report.getId();
            this.title = report.getTitle();
            this.user = report.getUser();
            this.reportControlNumber = report.getReportControlNumber();
            this.jiraTicketNumber = report.getJiraTicketNumber();
            this.reportVPossibility = report.getReportVPossibility();
            this.reportVGrade = report.getReportVGrade();
            this.reportPassword = report.getReportPassword();
            this.generalReview = report.getGeneralReview();
            this.diagnosisTable = report.getDiagnosisTable();
            this.fileExtension = report.getFileExtension();
            this.reportFilePath = report.getReportFilePath();
            this.created = report.getCreatedDate();
            this.reportOptionGroupsList = reportOptionGroupInfoList;
        }
    }

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ReportOptionGroupInfo {
        private final Long id;
        private final VItemInfo.VItemDetailGroupInfo vItemDetailGroupInfo;
        private final List<ReportOptionInfo> reportOptionInfoList;

        public ReportOptionGroupInfo(
                ReportOptionGroup reportOptionGroup,
                VItemInfo.VItemDetailGroupInfo vItemDetailGroupInfo,
                List<ReportOptionInfo> reportOptionInfoList
        ) {
            this.id = reportOptionGroup.getId();
            this.vItemDetailGroupInfo = vItemDetailGroupInfo;
            this.reportOptionInfoList = reportOptionInfoList;
        }
    }

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ReportOptionInfo {
        private final Long id;
        private final String vName;
        private final Integer reportVCount;
        private final String reportVIssue;
        private final String reportVResponse;
        private final List<ReportOptionMethodInfo> reportOptionMethodInfoList;
        private final List<ReportOptionImageInfo> reportOptionImageInfoList;

        public ReportOptionInfo(ReportOption reportOption,
                                List<ReportOptionMethodInfo> reportOptionMethodInfoList,
                                List<ReportOptionImageInfo> reportOptionImageInfoList
                                ) {
            this.id = reportOption.getId();
            this.vName = reportOption.getVName();
            this.reportVCount = reportOption.getReportVCount();
            this.reportVIssue = reportOption.getReportVIssue();
            this.reportVResponse = reportOption.getReportVResponse();
            this.reportOptionMethodInfoList = reportOptionMethodInfoList;
            this.reportOptionImageInfoList = reportOptionImageInfoList;
        }

    }

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ReportOptionImageInfo {
        private final Long id;
        private final String fileUrl;
        private final String imageName;
        private final String extension;
        private final Integer ordering;
        private final String description;
        private final String caption;
        private final String filePath;

        public ReportOptionImageInfo(ReportOptionImage reportOptionImage, String url) {
            this.id = reportOptionImage.getId();
            this.fileUrl = url;
            this.imageName = reportOptionImage.getImageName();
            this.extension = reportOptionImage.getExtension();
            this.ordering = reportOptionImage.getOrdering();
            this.description = reportOptionImage.getDescription();
            this.caption = reportOptionImage.getCaption();
            this.filePath = reportOptionImage.getReportOptionImageFilePath();
        }
    }

    @Getter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ReportOptionMethodInfo {
        private final Long id;
        private final String methodName;
        private final String methodPackage;
        private final String methodDescription;
        private final Integer ordering;

        public ReportOptionMethodInfo(ReportOptionMethod reportOptionMethod) {
            this.id = reportOptionMethod.getId();
            this.methodName = reportOptionMethod.getMethodName();
            this.methodPackage = reportOptionMethod.getMethodPackage();
            this.methodDescription = reportOptionMethod.getMethodDescription();
            this.ordering = reportOptionMethod.getOrdering();
        }
    }
}
