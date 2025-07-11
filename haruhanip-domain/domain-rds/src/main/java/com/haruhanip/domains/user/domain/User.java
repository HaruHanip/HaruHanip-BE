package com.haruhanip.domains.user.domain;

import com.haruhanip.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "regist_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RegistStatus registStatus;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Builder(toBuilder = true)
    public User(Long userId, String email, String username, String profileImage,
                UserRole userRole, RegistStatus registStatus, LocalDate birthday) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.profileImage = profileImage;
        this.userRole = userRole;
        this.registStatus = registStatus;
        this.birthday = birthday;
    }

    public void makeUser() {
        this.email = UUID.randomUUID().toString() + "@example.com";
        this.userRole = UserRole.USER;
        this.registStatus = RegistStatus.NOT_YET;
    }

    public void registUser(String username, String email, String profileImage, LocalDate birthday) {
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
        this.birthday = birthday;
        this.registStatus = RegistStatus.REGISTERED;
    }

    public void updateUser(String username, String email, String profileImage, LocalDate birthday) {
        this.username = username;
        this.email = email;
        this.profileImage = profileImage;
        this.birthday = birthday;
    }
}
