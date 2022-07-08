package com.wemakeprice.vms.reportapi.domain.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthTokenReader authTokenReader;

    @Override
    @Transactional
    public AuthTokenInfo.Main retrieveAuthToken(String token) {
        var authToken = authTokenReader.getAuthToken(token);
        var user = authTokenReader.getUser(authToken.getUser().getId());
        return new AuthTokenInfo.Main(authToken, user);
    }
}
