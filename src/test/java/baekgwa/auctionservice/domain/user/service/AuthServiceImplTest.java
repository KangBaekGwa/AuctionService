package baekgwa.auctionservice.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import baekgwa.auctionservice.global.common.exception.CustomException;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.global.security.CustomUserDetails;
import baekgwa.auctionservice.integration.SpringBootTestSupporter;
import baekgwa.auctionservice.integration.UserFactory;
import baekgwa.auctionservice.web.authentication.dto.RequestAuthDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class AuthServiceImplTest extends SpringBootTestSupporter {

    @DisplayName("[Success] 로그인을 정상적으로 성공하면, ContextHolder 에서 관리됩니다.")
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

    @DisplayName("[Fail] 잘못된 로그인 아이디로 로그인을 시도하면, 실패하고 CustomException 이 발생 됩니다.")
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

    @DisplayName("[Fail] 잘못된 로그인 비밀번호로 로그인을 시도하면, 실패하고 CustomException 이 발생 됩니다. 원인은 자세히 제공되지 않습니다.")
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

    private RequestAuthDto.Login createLoginData(String loginId, String password) {
        return RequestAuthDto.Login.builder()
                .loginId(loginId)
                .password(password)
                .build();
    }
}