package com.wemakeprice.vms.reportapi.domain.report;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import com.wemakeprice.vms.reportapi.domain.users.User;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class ReportInfo {

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
            this.reportOptionGroupsList = reportOptionGroupInfoList;
        }
    }

    @Getter
    @ToString
    public static class ReportOptionGroupInfo {
        private final Long id;
        private final VItemDetailGroup vItemDetailGroup;
        private final List<ReportOptionInfo> reportOptionInfoList;

        public ReportOptionGroupInfo(
                ReportOptionGroup reportOptionGroup,
                VItemDetailGroup vItemDetailGroup,
                                     List<ReportOptionInfo> reportOptionInfoList
        ) {
            this.id = reportOptionGroup.getId();
            this.vItemDetailGroup = vItemDetailGroup;
            this.reportOptionInfoList = reportOptionInfoList;
        }
    }

    @Getter
    @ToString
    public static class ReportOptionInfo {
        private final Long id;
        private final String vName;
        private final Integer reportVCount;
        private final String reportVIssue;
        private final String reportVResponse;

        public ReportOptionInfo(ReportOption reportOption) {
            this.id = reportOption.getId();
            this.vName = reportOption.getVName();
            this.reportVCount = reportOption.getReportVCount();
            this.reportVIssue = reportOption.getReportVIssue();
            this.reportVResponse = reportOption.getReportVResponse();
        }

        public ReportOption toEntity(ReportOptionGroup reportOptionGroup) {
            return ReportOption.builder()
                    .vName(vName)
                    .reportVResponse(reportVResponse)
                    .reportOptionGroup(reportOptionGroup)
                    .reportVCount(reportVCount)
                    .reportVIssue(reportVIssue)
                    .build();
        }
    }
}
