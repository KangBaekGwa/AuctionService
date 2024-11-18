package baekgwa.auctionservice.domain.authentication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baekgwa.auctionservice.model.user.entity.User;
import baekgwa.auctionservice.global.common.exception.CustomException;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.global.security.CustomUserDetails;
import baekgwa.auctionservice.integration.SpringBootTestSupporter;
import baekgwa.auctionservice.integration.factorymethod.UserFactory;
import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto;
import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto.SignUp;
import baekgwa.auctionservice.model.user.repository.UserRepository;
import baekgwa.auctionservice.model.userprofile.entity.UserProfile;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class AuthServiceImplTest extends SpringBootTestSupporter {

    @DisplayName("로그인을 정상적으로 성공하면, ContextHolder 에서 관리됩니다.")
    @Test
    void loginTest1() {
        // given
        userRepository.save(UserFactory.createNewAdmin("test1", "!test1234", passwordEncoder));
        RequestAuthDto.Login loginData = createLoginData("test1", "!test1234");

        // when
        authService.login(loginData);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        // then
        assertThat(authentication).isNotNull();
        assertThat(principal).isInstanceOf(CustomUserDetails.class);
        UserDetails userDetails = (UserDetails) principal;
        assertThat(userDetails.getUsername()).isEqualTo("test1");
    }

    @DisplayName("잘못된 로그인 아이디로 로그인을 시도하면, 실패하고 CustomException 이 발생 됩니다.")
    @Test
    void loginTest2() {
        // given
        userRepository.save(UserFactory.createNewAdmin("test1", "!test1234", passwordEncoder));
        RequestAuthDto.Login loginData = createLoginData("test2", "!test1234");

        // when // then
        assertThatThrownBy(() -> authService.login(loginData))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(BaseResponseCode.AUTHENTICATION_LOGIN_FAIL);
    }

    @DisplayName("잘못된 로그인 비밀번호로 로그인을 시도하면, 실패하고 CustomException 이 발생 됩니다. 원인은 자세히 제공되지 않습니다.")
    @Test
    void loginTest3() {
        // given
        userRepository.save(UserFactory.createNewAdmin("test1", "!test1234", passwordEncoder));
        RequestAuthDto.Login loginData = createLoginData("test1", "!test4445");

        // when // then
        assertThatThrownBy(() -> authService.login(loginData))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(BaseResponseCode.AUTHENTICATION_LOGIN_FAIL);
    }

    @DisplayName("회원가입을 진행 합니다.")
    @Test
    void signUpTest1() {
        // given
        RequestAuthDto.SignUp signUpData = SignUp
                .builder()
                .loginId("test1")
                .password("!test1234")
                .name("user1")
                .nickName("userNickName")
                .email("email@email.com")
                .phone("01011112222")
                .build();

        // when
        authService.signUp(signUpData);

        // then
        Optional<User> findUserData = userRepository.findByLoginId("test1");
        assertThat(findUserData).isPresent();
        assertThat(userProfileRepository.findById(findUserData.get().getId())).isPresent();
    }

    @DisplayName("회원가입을 진행 합니다. 중복된 회원가입 데이터가 있으면, 오류가 발생됩니다.")
    @Test
    void signUpTest2() {
        // given
        RequestAuthDto.SignUp signUpData = SignUp
                .builder()
                .loginId("test1")
                .password("!test1234")
                .name("user1")
                .nickName("userNickName")
                .email("email@email.com")
                .phone("01011112222")
                .build();

        User newUser = UserFactory.createNewAdmin("test1", "!test1234", passwordEncoder);
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, "user1",
                "userNickName", "email@email.com", "01011112222");
        userRepository.save(newUser);
        userProfileRepository.save(newUserProfile);

        // when // then
        assertThatThrownBy(() -> authService.signUp(signUpData))
                .isInstanceOf(CustomException.class)
                .extracting("code")
                .isEqualTo(BaseResponseCode.DUPLICATED_SIGNUP_DATA);
    }

    private RequestAuthDto.Login createLoginData(String loginId, String password) {
        return RequestAuthDto.Login.builder()
                .loginId(loginId)
                .password(password) 
                .build();
    }
}