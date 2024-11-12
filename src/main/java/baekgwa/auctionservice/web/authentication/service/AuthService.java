package baekgwa.auctionservice.web.authentication.service;

import baekgwa.auctionservice.web.authentication.dto.RequestAuthDto;

public interface AuthService {

    void login(RequestAuthDto.Login loginData);

}
