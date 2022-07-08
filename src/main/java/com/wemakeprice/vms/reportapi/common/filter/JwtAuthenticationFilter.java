package com.wemakeprice.vms.reportapi.common.filter;

import com.wemakeprice.vms.reportapi.common.utils.UserAuthentication;
import com.wemakeprice.vms.reportapi.domain.users.User;
import com.wemakeprice.vms.reportapi.domain.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.isNotEmpty(jwt)) {

                User user = userService.retrieveAuthToken(jwt).getUser().toEntity();
                UserAuthentication authentication = new UserAuthentication(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                if (StringUtils.isEmpty(jwt)) {
                    request.setAttribute("unauthorization", "401 인증키 없음.");
                }
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Token ")) {
            return bearerToken.substring("Token ".length());
        }
        return null;
    }
}
