package com.wemakeprice.vms.reportapi.web.dto;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

public class ReportDto {

    @Getter
    @Setter
    @ToString
    public static class GenerateReportRequest {
        private String title;
        private String jira_ticket_number;
        private Report.Vulnerability report_v_possibility;
        private Report.Grade report_v_grade;
        private String general_review;
        private Long diagnosis_table_id;

        private List<RegisterReportOptionGroup> report_option_group_list;

        public ReportCommand.GenerateReportRequest toCommand() {
            return ReportCommand.GenerateReportRequest.builder()
                    .title(title)
                    .jiraTicketNumber(jira_ticket_number)
                    .reportVPossibility(report_v_possibility)
                    .reportVGrade(report_v_grade)
                    .generalReview(general_review)
                    .diagnosisTableId(diagnosis_table_id)
                    .reportOptionGroupRequestList(report_option_group_list.stream().map(optionGroup ->
                        ReportCommand.GenerateReportGroupRequest.builder()
                                .vItemDetailGroupId(optionGroup.v_item_detail_group_id)
                                .generateReportOptionGroupRequests(optionGroup.report_option_list.stream().map(
                                        options -> ReportCommand.GenerateReportOptionGroupRequest.builder()
                                                .vName(options.v_name)
                                                .reportVCount(options.report_v_count)
                                                .reportVResponse(options.report_v_response)
                                                .reportVIssue(options.report_v_issue)
                                                .ordering(options.ordering)
                                        .build()
                                ).collect(Collectors.toList()))
                                .build()
                    ).collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterReportOptionGroup {

        private Long v_item_detail_group_id;
        private List<RegisterReportOption> report_option_list;

        public ReportCommand.GenerateReportGroupRequest toCommand() {
            return ReportCommand.GenerateReportGroupRequest.builder()
                    .vItemDetailGroupId(v_item_detail_group_id)
                    .generateReportOptionGroupRequests(report_option_list.stream().map(options -> ReportCommand.GenerateReportOptionGroupRequest.builder()
                            .vName(options.v_name)
                            .reportVCount(options.report_v_count)
                            .reportVIssue(options.report_v_issue)
                            .reportVResponse(options.report_v_response)
                            .ordering(options.ordering)
                            .build()).collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterReportOption {
        private String v_name;
        private Integer report_v_count;
        private String report_v_issue;
        private String report_v_response;
        private Integer ordering;

        public ReportCommand.GenerateReportOptionGroupRequest toCommand() {
            return ReportCommand.GenerateReportOptionGroupRequest.builder()
                    .vName(v_name)
                    .reportVCount(report_v_count)
                    .reportVIssue(report_v_issue)
                    .reportVResponse(report_v_response)
                    .ordering(ordering)
                    .build();
        }
    }
}
