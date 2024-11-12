package baekgwa.auctionservice.web.authentication.controller;

import baekgwa.auctionservice.domain.user.service.AuthService;
import baekgwa.auctionservice.global.common.response.BaseResponse;
import baekgwa.auctionservice.web.authentication.dto.RequestAuthDto;
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
}
