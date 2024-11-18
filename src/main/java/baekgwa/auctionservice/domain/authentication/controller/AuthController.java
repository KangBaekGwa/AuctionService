package baekgwa.auctionservice.domain.authentication.controller;

import baekgwa.auctionservice.domain.authentication.service.AuthService;
import baekgwa.auctionservice.global.common.response.BaseResponse;
import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<Void> loginUnion(
            @Validated @RequestBody RequestAuthDto.Login loginData
    ) {
        authService.login(loginData);
        return BaseResponse.ok();
    }

    @PostMapping("/signup")
    public BaseResponse<Void> signupUnion(
            @Validated @RequestBody RequestAuthDto.SignUp signUpData
    ) {
        authService.signUp(signUpData);
        return BaseResponse.ok();
    }
}
