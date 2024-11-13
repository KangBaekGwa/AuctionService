package baekgwa.auctionservice.domain.userprofile.entity;

import static org.assertj.core.api.Assertions.assertThat;

import baekgwa.auctionservice.domain.user.entity.User;
import baekgwa.auctionservice.integration.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserProfileTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("[Success] 새로운 사용자 프로파일을 생성합니다. 등급은 브론즈로 바로 부여됩니다.")
    @Test
    void createNewUserProfile() {
        // given
        User newUser = UserFactory.createNewAdmin("test1", "1234", passwordEncoder);

        // when
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, "UserA", "백과",
                "email@email.com", "01012341234");

        // then
        assertThat(newUserProfile).isNotNull()
                .extracting("name", "nickName", "email", "phone", "grade")
                .contains("UserA", "백과", "email@email.com", "01012341234", UserGrade.BRONZE);
    }

    @DisplayName("[Success] 사용자의 등급을 변경합니다.")
    @Test
    void updateGrade() {
        // given
        User newUser = UserFactory.createNewAdmin("test1", "1234", passwordEncoder);
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, "UserA", "백과",
                "email@email.com", "01012341234");

        // when
        newUserProfile.updateGrade(UserGrade.DIAMOND);

        // then
        assertThat(newUserProfile.getGrade()).isEqualTo(UserGrade.DIAMOND);
    }

    @DisplayName("[Success] 사용자의 프로파일 정보를 변경합니다.")
    @Test
    void updateUserProfile() {
        // given
        User newUser = UserFactory.createNewAdmin("test1", "1234", passwordEncoder);
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, "UserA", "백과",
                "email@email.com", "01012341234");

        // when
        newUserProfile.updateUserProfile("updatedNickName", "updatedName", "update@update.com", "01011112222");

        // then
        assertThat(newUserProfile)
                .extracting("nickName", "name", "email", "phone")
                .contains(
                        "updatedNickName", "updatedName", "update@update.com", "01011112222"
                );
    }
}