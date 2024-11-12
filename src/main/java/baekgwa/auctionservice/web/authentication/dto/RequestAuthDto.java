package baekgwa.auctionservice.web.authentication.dto;

import baekgwa.auctionservice.global.validation.annotation.UserLoginId;
import baekgwa.auctionservice.global.validation.annotation.UserPassword;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestAuthDto {

    @Getter
    @NoArgsConstructor
    public static class Login {

        @UserLoginId
        private String loginId;

        @UserPassword
        private String password;

        @Builder
        private Login(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }
    }
}
