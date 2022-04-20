package com.wemakeprice.vms.reportapi.common.utils.wkms.service;

import com.wemakeprice.vms.reportapi.common.utils.wkms.WkmsProperties;
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
    public Map<String, String> getWkmsData() {
            var uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(properties.getUrl())
                .path("/api")
                .queryParam("key", properties.getKey())
                .build(false)
                .toUriString();
        HttpEntity entity = getHttpEntity();

        var response = restTemplate.exchange(uri, HttpMethod.GET, entity, WkmsResponse.class);
        List<WkmsData> wkmsDataList = response.getBody().getData();
        return wkmsDataList.get(0).getValue();
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + properties.getToken());
        return new HttpEntity(headers);
    }
}
