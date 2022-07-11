package com.wemakeprice.vms.reportapi.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

public class ReportDto {

    @Getter
    @Setter
    @ToString
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class GenerateReportRequest {
        private String title;
        private String jiraTicketNumber;
        private Report.Vulnerability reportVPossibility;
        private Report.Grade reportVGrade;
        private String generalReview;
        private Long diagnosisTableId;

        private List<RegisterReportOptionGroup> reportOptionGroupList;

        public ReportCommand.GenerateReportRequest toCommand() {
            return ReportCommand.GenerateReportRequest.builder()
                    .title(title)
                    .jiraTicketNumber(jiraTicketNumber)
                    .reportVPossibility(reportVPossibility)
                    .reportVGrade(reportVGrade)
                    .generalReview(generalReview)
                    .diagnosisTableId(diagnosisTableId)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterReportOptionGroup {


        public ReportCommand.GenerateReportGroupRequest toCommand() {
            return ReportCommand.GenerateReportGroupRequest.builder()
                    .build();
        }
    }
}
