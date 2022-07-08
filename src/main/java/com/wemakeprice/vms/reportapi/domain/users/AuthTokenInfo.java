package com.wemakeprice.vms.reportapi.domain.users;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;


public class  AuthTokenInfo {

    @Getter
    @ToString
    public static class Main {
        private final String id;
        private final UserInfo user;
        private final Instant created;

        public Main(AuthToken authToken, UserInfo user) {
            this.id = authToken.getId();
            this.user = user;
            this.created = authToken.getCreated();
        }

        public AuthToken toEntity(User user) {
            return AuthToken.builder()
                    .id(id)
                    .created(created)
                    .user(user)
                    .build();
        }
    }


    @Getter
    @ToString
    public static class UserInfo {
        private final Long id;
        private final Boolean isSuperuser;
        private final Boolean isStaff;
        private final Boolean isActive;
        private final String username;
        private final String key;
        private final String displayName;

        public UserInfo(User user) {
            this.id = user.getId();
            this.isSuperuser = user.getIsSuperuser();
            this.isStaff = user.getIsStaff();
            this.isActive = user.getIsActive();
            this.username = user.getUsername();
            this.key = user.getKey();
            this.displayName = user.getDisplayName();
        }

        public User toEntity() {
            return User.builder()
                    .id(id)
                    .isActive(isActive)
                    .isSuperuser(isSuperuser)
                    .username(username)
                    .key(key)
                    .displayName(displayName)
                    .build();
        }
    }
}
