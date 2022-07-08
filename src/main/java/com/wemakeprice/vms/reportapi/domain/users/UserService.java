package com.wemakeprice.vms.reportapi.domain.users;

public interface UserService {

    AuthTokenInfo.Main retrieveAuthToken(String token);
}
