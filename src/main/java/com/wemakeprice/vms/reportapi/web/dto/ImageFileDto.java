package com.wemakeprice.vms.reportapi.web.dto;

import com.wemakeprice.vms.reportapi.annotation.ValidFile;
import com.wemakeprice.vms.reportapi.domain.report.ReportCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ImageFileDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterFile {
        private List<ReportOptionImage> files;
        private Long report_id;
    }

    @Getter
    @Setter
    @ToString
    public static class ReportOptionImage {

        @ValidFile
        @NotNull
        private MultipartFile file;

        private Long report_option_id;
        private Integer ordering;

        @Size(min = 1, max = 1000, message = "최소 Size= 1, 최대 Size=100 입니다.")
        private String caption;
        private String description;

        public ReportCommand.GenerateReportOptionImageRequest toCommand() {
            return ReportCommand.GenerateReportOptionImageRequest.builder()
                    .caption(caption)
                    .ordering(ordering)
                    .description(description)
                    .report_option_id(report_option_id)
                    .build();
        }
    }
}
