package baekgwa.auctionservice.web.authentication.dto;

import baekgwa.auctionservice.global.validation.annotation.UserLoginId;
import baekgwa.auctionservice.global.validation.annotation.UserPassword;
import lombok.Builder;
import lombok.Getter;

public class RequestAuthDto {

    @Getter
    public static class Login {

        @UserLoginId
        private final String loginId;

        @UserPassword
        private final String password;

        @Builder
        private Login(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }
    }
}
