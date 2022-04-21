package com.wemakeprice.vms.reportapi.config;

import com.wemakeprice.vms.reportapi.common.utils.wkms.service.WkmsService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@RequiredArgsConstructor
@Setter
@Profile(value = "!local")
public class WkmsConfig {
    private final WkmsService wkmsService;
    private String url;
    private String driverClassName;

    @Bean
    public DataSource getDataSource() {
        var wkmsData = wkmsService.getWkmsData();

        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(wkmsData.getUsername())
                .password(wkmsData.getPassword())
                .build();
    }
}
