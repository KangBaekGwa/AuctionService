package baekgwa.auctionservice.integration;

import baekgwa.auctionservice.domain.user.entity.User;
import baekgwa.auctionservice.domain.user.entity.UserRole;
import baekgwa.auctionservice.domain.user.entity.UserStatus;
import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFactory {

    /**
     *
     * @param loginId
     * @param password
     * @return ADMIN 권한, Active 상태의 회원을 생성 합니다.
     */
    public static User createNewAdmin(String loginId, String password, PasswordEncoder passwordEncoder) {
        return User
                .builder()
                .loginId(loginId)
                .password(password)
                .uuid(UUID.randomUUID().toString())
                .role(UserRole.ADMIN)
                .status(UserStatus.ACTIVE)
                .passwordEncoder(passwordEncoder)
                .build();
    }
}
