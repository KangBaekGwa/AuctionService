package baekgwa.auctionservice.model.userprofile.repository;

import static org.assertj.core.api.Assertions.assertThat;

import baekgwa.auctionservice.integration.SpringBootTestSupporter;
import baekgwa.auctionservice.integration.factorymethod.UserFactory;
import baekgwa.auctionservice.model.user.entity.User;
import baekgwa.auctionservice.model.userprofile.entity.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserProfileRepositoryTest extends SpringBootTestSupporter {

    @DisplayName("[Success] 닉네임, 이메일, 휴대폰 중 하나라도 중복되는게 있는지 확인합니다.")
    @Test
    void existsByNickNameOrEmailOrPhone() {
        // given
        User userData = UserFactory.createNewAdmin("test", "!test1234", passwordEncoder);
        UserProfile newUserProfileData = UserProfile.createNewUserProfile(userData, "테스터", "닉네임",
                "email@email.com", "01011112222");
        userRepository.save(userData);
        userProfileRepository.save(newUserProfileData);

        // when
        Boolean resultNickName = userProfileRepository.existsByNickNameOrEmailOrPhone("닉네임",
                "email2@email.com", "01033334444");
        Boolean resultEmail = userProfileRepository.existsByNickNameOrEmailOrPhone("닉네임2",
                "email@email.com", "01033334444");
        Boolean resultPhone = userProfileRepository.existsByNickNameOrEmailOrPhone("닉네임2",
                "email2@email.com", "01011112222");
        Boolean resultAll = userProfileRepository.existsByNickNameOrEmailOrPhone("닉네임",
                "email@email.com", "01011112222");

        // then
        assertThat(resultNickName).isTrue();
        assertThat(resultEmail).isTrue();
        assertThat(resultPhone).isTrue();
        assertThat(resultAll).isTrue();
    }

    @DisplayName("[Success] 닉네임, 이메일, 휴대폰 중 하나라도 중복되는게 있는지 확인합니다. 없다면 False 가 반환됩니다.")
    @Test
    void existsByNickNameOrEmailOrPhone1() {
        // given
        User userData = UserFactory.createNewAdmin("test", "!test1234", passwordEncoder);
        UserProfile newUserProfileData = UserProfile.createNewUserProfile(userData, "테스터", "닉네임",
                "email@email.com", "01011112222");
        userRepository.save(userData);
        userProfileRepository.save(newUserProfileData);

        // when
        Boolean result = userProfileRepository.existsByNickNameOrEmailOrPhone("닉네임2",
                "email2@email.com", "01033334444");

        // then
        assertThat(result).isFalse();
    }
}