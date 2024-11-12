package baekgwa.auctionservice.web.authentication.service;

import baekgwa.auctionservice.global.common.exception.CustomException;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.web.authentication.dto.RequestAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    @Override
    public void login(RequestAuthDto.Login loginData) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginData.getLoginId(),
                loginData.getPassword()
        );

        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (InternalAuthenticationServiceException |
                 BadCredentialsException e) {
            throw new CustomException(BaseResponseCode.AUTHENTICATION_LOGIN_FAIL);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
