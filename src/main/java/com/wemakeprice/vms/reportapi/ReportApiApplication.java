package com.wemakeprice.vms.reportapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class ReportApiApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReportApiApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

}
