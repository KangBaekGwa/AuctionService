package baekgwa.auctionservice.domain.authentication.service;

import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto;

public interface AuthService {

    void login(RequestAuthDto.Login loginData);

    void signUp(RequestAuthDto.SignUp signUpData);
}
