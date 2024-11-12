package baekgwa.auctionservice.global.security;

import baekgwa.auctionservice.domain.user.entity.User;
import baekgwa.auctionservice.domain.user.repository.UserRepository;
import baekgwa.auctionservice.global.common.exception.CustomException;
import baekgwa.auctionservice.global.common.response.BaseResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userData = userRepository.findByLoginId(username).orElseThrow(
                () -> new CustomException(BaseResponseCode.AUTHENTICATION_LOGIN_FAIL)
        );

        return new CustomUserDetails(userData);
    }
}
