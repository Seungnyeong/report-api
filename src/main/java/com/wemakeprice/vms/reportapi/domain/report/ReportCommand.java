package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.option.ReportOption;
import com.wemakeprice.vms.reportapi.domain.report.optionGroup.ReportOptionGroup;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class ReportCommand {

    @Getter
    @Builder
    @ToString
    public static class GenerateReportRequest {
        private final String title;
        private final String jiraTicketNumber;
        private final Report.Vulnerability reportVPossibility;
        private final Report.Grade reportVGrade;
        private final String generalReview;
        private final Long diagnosisTableId;
        private final List<GenerateReportGroupRequest> reportOptionGroupRequestList;

        public Report toEntity(DiagnosisTable diagnosisTable) {
            return Report.builder()
                    .title(title)
                    .diagnosisTable(diagnosisTable)
                    .reportFileName(null)
                    .generalReview(generalReview)
                    .jiraTicketNumber(jiraTicketNumber)
                    .reportVGrade(reportVGrade)
                    .reportVPossibility(reportVPossibility)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class GenerateReportGroupRequest {

        List<GenerateReportOptionGroupRequest> generateReportOptionGroupRequests;

        public ReportOptionGroup toEntity(Report report, VItemDetailGroup vItemDetailGroup) {
            return ReportOptionGroup.builder()
                    .report(report)
                    .vItemDetailGroup(vItemDetailGroup)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class GenerateReportOptionGroupRequest {
        private final String vName;
        private final Integer reportVCount;
        private final String reportVIssue;
        private final String reportVResponse;

        public ReportOption toEntity(ReportOptionGroup reportOptionGroup) {
            return ReportOption.builder()
                    .reportOptionGroup(reportOptionGroup)
                    .vName(vName)
                    .reportVCount(reportVCount)
                    .reportVResponse(reportVResponse)
                    .build();
        }
    }
}
