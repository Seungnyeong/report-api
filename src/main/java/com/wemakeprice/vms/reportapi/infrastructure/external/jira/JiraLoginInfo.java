package com.wemakeprice.vms.reportapi.infrastructure.external.jira;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JiraLoginInfo {
    private Session session;
    private LoginInfo loginInfo;

    @Getter
    @Setter
    public static class Session {
        private String name;
        private String value;
    }

    @Getter
    @Setter
    public static class LoginInfo {
        private Integer loginCount;
        private String previousLogInTime;
    }
}
