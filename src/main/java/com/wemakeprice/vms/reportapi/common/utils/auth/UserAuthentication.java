package com.wemakeprice.vms.reportapi.common.utils.auth;

import com.wemakeprice.vms.reportapi.domain.users.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    public UserAuthentication(User principal, String credentials) {
        super(principal , credentials);
    }

    public UserAuthentication(User principal, String credentials,
                              List<GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
