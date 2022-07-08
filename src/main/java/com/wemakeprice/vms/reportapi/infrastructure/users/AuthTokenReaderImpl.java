package com.wemakeprice.vms.reportapi.infrastructure.users;

import com.wemakeprice.vms.reportapi.common.exception.EntityNotFoundException;
import com.wemakeprice.vms.reportapi.domain.users.AuthToken;
import com.wemakeprice.vms.reportapi.domain.users.AuthTokenInfo;
import com.wemakeprice.vms.reportapi.domain.users.AuthTokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenReaderImpl implements AuthTokenReader {

    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    @Override
    public AuthToken getAuthToken(String token) {
        return authTokenRepository.findAuthTokenById(token).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AuthTokenInfo.UserInfo getUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return new AuthTokenInfo.UserInfo(user);
    }
}
