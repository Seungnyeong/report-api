package com.wemakeprice.vms.reportapi.common.utils.wkms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "wkms")
public class WkmsProperties {
    private String key;
    private String token;
    private String url;
}
