package com.wemakeprice.vms.reportapi.infrastructure.users;

import com.wemakeprice.vms.reportapi.domain.users.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    Optional<AuthToken> findAuthTokenById(String id);
}
