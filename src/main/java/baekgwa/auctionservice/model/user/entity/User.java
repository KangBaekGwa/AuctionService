package baekgwa.auctionservice.model.user.entity;

import baekgwa.auctionservice.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String uuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Builder
    private User(String loginId, String password, String uuid, UserRole role, UserStatus status, PasswordEncoder passwordEncoder) {
        this.loginId = loginId;
        this.password = passwordEncoder.encode(password);
        this.uuid = uuid;
        this.role = role;
        this.status = status;
    }

    public static User createNewUser(String loginId, String password, PasswordEncoder passwordEncoder) {
        return User
                .builder()
                .loginId(loginId)
                .password(password)
                .uuid(UUID.randomUUID().toString())
                .role(UserRole.NONE)
                .status(UserStatus.ACTIVE)
                .passwordEncoder(passwordEncoder)
                .build();
    }

    public void updateRole(UserRole role) {
        this.role = role;
    }

    public void updateStatus(UserStatus status) {
        this.status = status;
    }
}
