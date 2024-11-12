package baekgwa.auctionservice.domain.user.repository;


import baekgwa.auctionservice.domain.user.entity.User;
import baekgwa.auctionservice.domain.user.entity.UserRole;
import baekgwa.auctionservice.domain.user.entity.UserStatus;
import baekgwa.auctionservice.integration.SpringBootTestSupporter;
import baekgwa.auctionservice.integration.UserFactory;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
        Assertions.assertThat(findData).isPresent()
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
        Assertions.assertThat(findData).isEmpty();
    }
}