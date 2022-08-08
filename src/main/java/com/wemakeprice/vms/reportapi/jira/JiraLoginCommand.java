package com.wemakeprice.vms.reportapi.jira;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JiraLoginCommand {
    private String username;
    private String password;
}
