package baekgwa.auctionservice.integration;

import baekgwa.auctionservice.domain.user.repository.UserRepository;
import baekgwa.auctionservice.domain.userprofile.repository.UserProfileRepository;
import baekgwa.auctionservice.web.authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class SpringBootTestSupporter {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserProfileRepository userProfileRepository;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

}
