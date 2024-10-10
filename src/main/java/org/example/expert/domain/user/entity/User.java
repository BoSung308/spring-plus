package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;


@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(nullable = true)
    private String nickName;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User(String email, String password, String nickName, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.userRole = userRole;
    }

    private User(Long id, String email, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return User.builder()
                .email(authUser.getEmail())
                .id(authUser.getId())
                .userRole(UserRole.of(authUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst() // 47번부터 49번까지 gpt 이용했습니다..코드를 작성 못하겠습니다..
                .orElse(UserRole.ROLE_USER.name()))
                )
                .build();

    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}