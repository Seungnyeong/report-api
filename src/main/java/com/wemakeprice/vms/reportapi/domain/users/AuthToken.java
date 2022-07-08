package com.wemakeprice.vms.reportapi.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "authtoken_token")
@Getter
@NoArgsConstructor
public class AuthToken {
    @Id
    @Column(name = "`key`", nullable = false, length = 40)
    private String id;

    @Column(name = "created", nullable = false)
    private Instant created;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public AuthToken(String id, Instant created, User user) {
        this.id = id;
        this.created = created;
        this.user = user;
    }


}