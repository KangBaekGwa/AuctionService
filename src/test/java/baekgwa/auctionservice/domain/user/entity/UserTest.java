package baekgwa.auctionservice.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import baekgwa.auctionservice.integration.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserTest {

    @DisplayName("[Success] 새로운 사용자를 생성합니다. 사용자는 기본적으로 권한은 없고, Uuid는 자동으로 생성 됩니다.")
    @Test
    void createNewUser() {
        // given
        String loginId = "test1";
        String password = "1234";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // when
        User newUser = User.createNewUser(loginId, password, passwordEncoder);

        // then
        assertThat(newUser)
                .extracting("loginId", "role", "status")
                .contains(loginId, UserRole.NONE, UserStatus.ACTIVE);
        assertThat(newUser.getUuid()).isNotNull();
    }

    @DisplayName("[Success] 새로운 관리자를 생성합니다.")
    @Test
    void createNewAdmin() {
        // given
        String loginId = "test1";
        String password = "1234";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // when
        User newUser = UserFactory.createNewAdmin(loginId, password, passwordEncoder);

        // then
        assertThat(newUser)
                .extracting("loginId", "role", "status")
                .contains(loginId, UserRole.ADMIN, UserStatus.ACTIVE);
        assertThat(newUser.getUuid()).isNotNull();
    }

    @DisplayName("[Success] 회원의 Role 값을 변경합니다.")
    @Test
    void updateRole() {
        // given
        String loginId = "test1";
        String password = "1234";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newUser = User.createNewUser(loginId, password, passwordEncoder);

        // when
        newUser.updateRole(UserRole.BUYER);

        // then
        assertThat(newUser)
                .extracting("loginId", "role", "status")
                .contains(loginId, UserRole.BUYER, UserStatus.ACTIVE);
        assertThat(newUser.getUuid()).isNotNull();
    }

    @DisplayName("[Success] 회원의 Role 값을 변경합니다.")
    @Test
    void updateStatus() {
        // given
        String loginId = "test1";
        String password = "1234";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User newUser = User.createNewUser(loginId, password, passwordEncoder);

        // when
        newUser.updateStatus(UserStatus.BLOCKED);

        // then
        assertThat(newUser)
                .extracting("loginId", "role", "status")
                .contains(loginId, UserRole.NONE, UserStatus.BLOCKED);
        assertThat(newUser.getUuid()).isNotNull();
    }
}