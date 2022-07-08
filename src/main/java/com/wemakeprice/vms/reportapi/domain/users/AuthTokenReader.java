package com.wemakeprice.vms.reportapi.domain.users;

import java.util.Optional;

public interface AuthTokenReader {
    AuthToken getAuthToken(String token);
    AuthTokenInfo.UserInfo getUser(Long userId);
}
