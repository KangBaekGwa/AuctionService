package baekgwa.auctionservice.domain.authentication.service;

import baekgwa.auctionservice.global.common.exception.CustomException;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import baekgwa.auctionservice.domain.authentication.dto.RequestAuthDto;
import baekgwa.auctionservice.model.user.entity.User;
import baekgwa.auctionservice.model.user.repository.UserRepository;
import baekgwa.auctionservice.model.userprofile.entity.UserProfile;
import baekgwa.auctionservice.model.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
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

    @Transactional
    @Override
    public void signUp(RequestAuthDto.SignUp signUpData) {
        if(duplicateCheck(signUpData)){
            throw new CustomException(BaseResponseCode.DUPLICATED_SIGNUP_DATA);
        }

        User newUser = User.createNewUser(signUpData.getLoginId(), signUpData.getPassword(), passwordEncoder);
        UserProfile newUserProfile = UserProfile.createNewUserProfile(newUser, signUpData.getName(),
                signUpData.getNickName(), signUpData.getEmail(), signUpData.getPhone());

        userRepository.save(newUser);
        userProfileRepository.save(newUserProfile);
    }

    private boolean duplicateCheck(RequestAuthDto.SignUp signUpData) {
        return userRepository.existsByLoginId(signUpData.getLoginId()) ||
                userProfileRepository.existsByNickNameOrEmailOrPhone(
                        signUpData.getNickName(), signUpData.getEmail(), signUpData.getPhone());
    }
}
