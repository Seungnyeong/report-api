package com.wemakeprice.vms.reportapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
    private String uploadDir;
    private String reportTemplateDir;
    private String reportResultDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getReportTemplateDir() {
        return reportTemplateDir;
    }

    public void setReportTemplateDir(String reportTemplateDir) {
        this.reportTemplateDir = reportTemplateDir;
    }

    public String getReportResultDir() {
        return reportResultDir;
    }

    public void setReportResultDir(String reportResultDir) {
        this.reportResultDir = reportResultDir;
    }
}
