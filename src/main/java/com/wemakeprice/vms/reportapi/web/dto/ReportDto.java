package com.wemakeprice.vms.reportapi.web.dto;

import com.wemakeprice.vms.reportapi.domain.report.Report;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class ReportDto {

    @Getter
    @Setter
    @ToString
    public static class GenerateReportRequest {

        @NotNull
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
                                .generateReportOptionGroupRequestList(optionGroup.report_option_list.stream().map(
                                        options -> ReportCommand.GenerateReportOptionGroupRequest.builder()
                                                .vName(options.v_name)
                                                .reportVCount(options.report_v_count)
                                                .reportVResponse(options.report_v_response)
                                                .reportVIssue(options.report_v_issue)
                                                .ordering(options.ordering)
                                                .generateReportOptionMethodRequestList(options.report_option_method_list.stream()
                                                    .map(method -> ReportCommand.GenerateReportOptionMethodRequest.builder()
                                                        .methodPackage(method.method_package)
                                                        .methodName(method.method_name)
                                                        .methodDescription(method.method_description)
                                                        .ordering(method.ordering)
                                                .build()).collect(Collectors.toList()))
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
    public static class UpdateReportMainRequest {
        private Long report_id;
        private String title;
        private Report.Vulnerability report_v_possibility;
        private Report.Grade report_v_grade;
        private String general_review;

        public ReportCommand.GenerateReportRequest toCommand() {
            return ReportCommand.GenerateReportRequest.builder()
                    .id(report_id)
                    .title(title)
                    .reportVPossibility(report_v_possibility)
                    .reportVGrade(report_v_grade)
                    .generalReview(general_review)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterReportOptionGroup {
        private Long report_id;
        private Long v_item_detail_group_id;
        private List<RegisterReportOption> report_option_list;

        public ReportCommand.GenerateReportGroupRequest toCommand() {
            return ReportCommand.GenerateReportGroupRequest.builder()
                    .vItemDetailGroupId(v_item_detail_group_id)
                    .generateReportOptionGroupRequestList(report_option_list.stream()
                            .map(options -> ReportCommand.GenerateReportOptionGroupRequest.builder()
                                .vName(options.v_name)
                                .reportVCount(options.report_v_count)
                                .reportVIssue(options.report_v_issue)
                                .reportVResponse(options.report_v_response)
                                .generateReportOptionMethodRequestList(options.report_option_method_list.stream()
                                        .map(method -> ReportCommand.GenerateReportOptionMethodRequest.builder()
                                            .methodPackage(method.method_package)
                                            .methodName(method.method_name)
                                            .methodDescription(method.method_description)
                                            .ordering(method.ordering)
                                        .build()).collect(Collectors.toList()))
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
        private List<RegisterReportOptionMethod> report_option_method_list;
        private List<ImageFileDto.ReportOptionImage> report_option_image_list;

        public ReportCommand.GenerateReportOptionGroupRequest toCommand() {
            return ReportCommand.GenerateReportOptionGroupRequest.builder()
                    .vName(v_name)
                    .reportVCount(report_v_count)
                    .reportVIssue(report_v_issue)
                    .reportVResponse(report_v_response)
                    .generateReportOptionMethodRequestList(report_option_method_list.stream().map(method -> ReportCommand.GenerateReportOptionMethodRequest.builder()
                            .methodPackage(method.method_package)
                            .methodName(method.method_name)
                            .methodDescription(method.method_description)
                            .ordering(method.ordering)
                            .build()).collect(Collectors.toList()))
                    .ordering(ordering)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateReportOption {
        private Long report_option_id;
        private String v_name;
        private Integer report_v_count;
        private String report_v_issue;
        private String report_v_response;
        private Integer ordering;

        public ReportCommand.GenerateReportOptionGroupRequest toCommand() {
            return ReportCommand.GenerateReportOptionGroupRequest.builder()
                    .id(report_option_id)
                    .reportVResponse(report_v_response)
                    .reportVIssue(report_v_issue)
                    .reportVCount(report_v_count)
                    .ordering(ordering)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterReportOptionMethod {
        private String method_name;
        private String method_package;
        private String method_description;
        private Integer ordering;

        public ReportCommand.GenerateReportOptionMethodRequest toCommand() {
            return ReportCommand.GenerateReportOptionMethodRequest.builder()
                    .methodName(method_name)
                    .methodPackage(method_package)
                    .methodDescription(method_description)
                    .ordering(ordering)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateReportOptionMethod {
        private Long report_option_method_id;
        private String method_name;
        private String method_package;
        private String method_description;
        private Integer ordering;

        public ReportCommand.GenerateReportOptionMethodRequest toCommand() {
            return ReportCommand.GenerateReportOptionMethodRequest.builder()
                    .id(report_option_method_id)
                    .methodDescription(method_description)
                    .methodName(method_name)
                    .methodPackage(method_package)
                    .ordering(ordering)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ReportPasswordRequest {
        private Long report_id;
        private String password;
    }
}
