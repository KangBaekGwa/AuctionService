package baekgwa.auctionservice.model.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import baekgwa.auctionservice.model.user.entity.User;
import baekgwa.auctionservice.model.user.entity.UserRole;
import baekgwa.auctionservice.model.user.entity.UserStatus;
import baekgwa.auctionservice.integration.SpringBootTestSupporter;
import baekgwa.auctionservice.integration.factorymethod.UserFactory;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserRepositoryTest extends SpringBootTestSupporter {

    @DisplayName("[Success] 로그인 아이디로, 회원 정보를 검색합니다.")
    @Test
    void findByLoginId1() {
        // given
        userRepository.save(UserFactory.createNewAdmin("test1", "!test1234", passwordEncoder));

        // when
        Optional<User> findData = userRepository.findByLoginId("test1");

        // then
        assertThat(findData).isPresent()
                .get()
                .extracting("loginId", "role", "status")
                .contains(
                        "test1", UserRole.ADMIN, UserStatus.ACTIVE
                );
    }

    @DisplayName("[Success] 로그인 아이디로 검색 시, 회원 정보가 없으면 Optional 을 반환합니다.")
    @Test
    void findByLoginId2() {
        // given

        // when
        Optional<User> findData = userRepository.findByLoginId("test1");

        // then
        assertThat(findData).isEmpty();
    }

    @DisplayName("[Success] 로그인 아이디로 중복 확인을 진행합니다.")
    @Test
    void existsByLoginId() {
        // given
        userRepository.save(UserFactory.createNewAdmin("test1", "!test1234", passwordEncoder));

        // when
        Boolean result = userRepository.existsByLoginId("test1");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("[Success] 로그인 아이디로 중복 확인을 진행합니다. 없다면 false 가 반환됩니다.")
    @Test
    void existsByLoginId2() {
        // given

        // when
        Boolean result = userRepository.existsByLoginId("test1");

        // then
        assertThat(result).isFalse();
    }
}