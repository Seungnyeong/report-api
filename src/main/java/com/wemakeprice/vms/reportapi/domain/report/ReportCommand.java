package com.wemakeprice.vms.reportapi.domain.report;

import com.wemakeprice.vms.reportapi.domain.diagnosis.DiagnosisTable;
import com.wemakeprice.vms.reportapi.domain.report.image.FileResponse;
import com.wemakeprice.vms.reportapi.domain.report.image.ReportOptionImage;
import com.wemakeprice.vms.reportapi.domain.report.method.ReportOptionMethod;
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

        Long vItemDetailGroupId;
        List<GenerateReportOptionGroupRequest> generateReportOptionGroupRequestList;

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
        private final Integer ordering;
        private final List<GenerateReportOptionMethodRequest> generateReportOptionMethodRequestList;

        public ReportOption toEntity(ReportOptionGroup reportOptionGroup) {
            return ReportOption.builder()
                    .vName(vName)
                    .reportVCount(reportVCount)
                    .reportVIssue(reportVIssue)
                    .reportVResponse(reportVResponse)
                    .ordering(ordering)
                    .reportOptionGroup(reportOptionGroup)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class GenerateReportOptionMethodRequest {
        private final String methodName;
        private final String methodPackage;
        private final String methodDescription;
        private final Integer ordering;

        public ReportOptionMethod toEntity(ReportOption ReportOption) {
            return ReportOptionMethod.builder()
                    .methodName(methodName)
                    .methodPackage(methodPackage)
                    .methodDescription(methodDescription)
                    .ordering(ordering)
                    .reportOption(ReportOption)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class GenerateReportOptionImageRequest {
        private final String imageName;
        private final String extension;
        private final Integer ordering;
        private final String description;
        private final String caption;
        private final Long report_option_id;

        public ReportOptionImage toEntity(Report report, ReportOption reportOption, FileResponse file) {
            return ReportOptionImage.builder()
                    .reportOptionImageFilePath(file.getFilePath())
                    .report(report)
                    .reportOption(reportOption)
                    .imageName(file.getFileName())
                    .extension(file.getExtension())
                    .ordering(ordering)
                    .description(description)
                    .caption(caption)
                    .build();
        }
    }

}
