package com.wemakeprice.vms.reportapi.jira;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class JiraApiServiceImpl implements  JiraApiService {

    private static final String LOGIN_API_URL = "http://jira.wemakeprice.com/rest/auth/1/session";

    @Override
    public boolean isAuthUser(JiraLoginCommand command) {
        RestTemplate restTemplate = new RestTemplate();
        var response = restTemplate.postForEntity( LOGIN_API_URL, command, JiraLoginInfo.class );
        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
