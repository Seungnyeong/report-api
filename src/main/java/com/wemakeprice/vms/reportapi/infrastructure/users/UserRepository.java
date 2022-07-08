package com.wemakeprice.vms.reportapi.infrastructure.users;

import com.wemakeprice.vms.reportapi.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
