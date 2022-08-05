package com.wemakeprice.vms.reportapi.common.utils.wkms.service;

import com.wemakeprice.vms.reportapi.common.utils.wkms.WkmsProperties;
import com.wemakeprice.vms.reportapi.common.utils.wkms.dto.CryptoData;
import com.wemakeprice.vms.reportapi.common.utils.wkms.dto.CryptoResponse;
import com.wemakeprice.vms.reportapi.common.utils.wkms.dto.WkmsData;
import com.wemakeprice.vms.reportapi.common.utils.wkms.dto.WkmsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WkmsServiceImpl implements WkmsService {

    private final WkmsProperties properties;
    private final RestTemplate restTemplate;

    @Override
    public WkmsData.Value getWkmsData() {
        var uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(properties.getUrl())
                .path("/api")
                .queryParam("key", properties.getKey())
                .build(false)
                .toUriString();
        HttpEntity entity = getHttpEntity(properties.getToken());

        var response = restTemplate.exchange(uri, HttpMethod.GET, entity, WkmsResponse.class);
        List<WkmsData> wkmsDataList = response.getBody().getData();
        return wkmsDataList.get(0).getValue();
    }

    @Override
    public CryptoData.Value getCryptoData() {
        var uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(properties.getUrl())
                .path("/api/v2")
                .queryParam("key", properties.getCryptoKey())
                .build(false)
                .toUriString();
        HttpEntity entity = getHttpEntity(properties.getCryptoKeyToken());
        var response = restTemplate.exchange(uri, HttpMethod.GET, entity, CryptoResponse.class);
        List<Object> wkmsDataList = response.getBody().getData();
        Map<String, Object> data = (Map<String, Object>) wkmsDataList.get(0);
        Map<String, String> value = (Map<String, String>) data.get("value");

        return CryptoData.Value.builder()
                .ETC_KEY(value.get("ETC_KEY"))
                .ETC_KEY_IV(value.get("ETC_KEY_IV"))
                .build();
    }

    private HttpEntity getHttpEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + token);
        return new HttpEntity(headers);
    }
}
