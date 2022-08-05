package com.wemakeprice.vms.reportapi.config;

import com.wemakeprice.vms.reportapi.common.utils.wkms.service.WkmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CryptoKeyConfig {
    private final WkmsService wkmsService;
    public static String KEY;
    public static String IV;

    @Bean void setCrypto() {
        var cryptoData = wkmsService.getCryptoData();
        KEY = cryptoData.getETC_KEY();
        IV = cryptoData.getETC_KEY_IV();
    }
}
