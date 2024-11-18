package baekgwa.auctionservice.domain.authentication.dto;

import baekgwa.auctionservice.global.validation.annotation.UserEmail;
import baekgwa.auctionservice.global.validation.annotation.UserLoginId;
import baekgwa.auctionservice.global.validation.annotation.UserName;
import baekgwa.auctionservice.global.validation.annotation.UserNickName;
import baekgwa.auctionservice.global.validation.annotation.UserPassword;
import baekgwa.auctionservice.global.validation.annotation.UserPhone;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RequestAuthDto {

    @Getter
    @NoArgsConstructor
    public static class Login {

        @UserLoginId private String loginId;
        @UserPassword private String password;

        @Builder
        private Login(String loginId, String password) {
            this.loginId = loginId;
            this.password = password;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SignUp {
        @UserLoginId private String loginId;
        @UserPassword private String password;
        @UserName private String name;
        @UserNickName private String nickName;
        @UserEmail private String email;
        @UserPhone private String phone;

        @Builder
        private SignUp(String loginId, String password, String name, String nickName, String email,
                String phone) {
            this.loginId = loginId;
            this.password = password;
            this.name = name;
            this.nickName = nickName;
            this.email = email;
            this.phone = phone;
        }
    }
}
