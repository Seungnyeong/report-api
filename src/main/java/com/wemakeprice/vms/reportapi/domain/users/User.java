package com.wemakeprice.vms.reportapi.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Column(name = "is_superuser", nullable = false)
    private Boolean isSuperuser = false;

    @Column(name = "username", nullable = false, length = 150)
    private String username;

    @Column(name = "first_name", nullable = false, length = 150)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "is_staff", nullable = false)
    private Boolean isStaff = false;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "date_joined", nullable = false)
    private Instant dateJoined;

    @Column(name = "avatar_url", length = 400)
    private String avatarUrl;

    @Column(name = "`key`", length = 45)
    private String key;

    @Column(name = "display_name", length = 10)
    private String displayName;

    @Column(name = "group_name", length = 10)
    private String groupName;

    @Builder
    public User(Long id, String password, Instant lastLogin, Boolean isSuperuser, String username, String firstName, String lastName, String email, Boolean isStaff, Boolean isActive, Instant dateJoined, String avatarUrl, String key, String displayName, String groupName) {
        this.id = id;
        this.password = password;
        this.lastLogin = lastLogin;
        this.isSuperuser = isSuperuser;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isStaff = isStaff;
        this.isActive = isActive;
        this.dateJoined = dateJoined;
        this.avatarUrl = avatarUrl;
        this.key = key;
        this.displayName = displayName;
        this.groupName = groupName;
    }
}