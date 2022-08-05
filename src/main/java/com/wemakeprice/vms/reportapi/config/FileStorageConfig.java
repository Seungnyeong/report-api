package com.wemakeprice.vms.reportapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private String uploadDir;
    private String reportTemplateFile;
    private String reportResultDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getReportTemplateFile() {
        return reportTemplateFile;
    }

    public void setReportTemplateFile(String reportTemplateFile) {
        this.reportTemplateFile = reportTemplateFile;
    }

    public String getReportResultDir() {
        return reportResultDir;
    }

    public void setReportResultDir(String reportResultDir) {
        this.reportResultDir = reportResultDir;
    }
}
