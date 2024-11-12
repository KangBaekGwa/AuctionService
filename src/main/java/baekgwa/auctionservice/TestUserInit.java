package baekgwa.auctionservice;

import baekgwa.auctionservice.domain.user.entity.User;
import baekgwa.auctionservice.domain.user.entity.UserRole;
import baekgwa.auctionservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class TestUserInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 실행시 사용할 Admin 용 계정 생성
     */
    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        User adminUser = User.createNewUser("admin1", "!admin1234", passwordEncoder);
        adminUser.updateRole(UserRole.ADMIN);
        userRepository.save(adminUser);

        User buyerUser = User.createNewUser("buyer1", "!buyer1234", passwordEncoder);
        adminUser.updateRole(UserRole.BUYER);
        userRepository.save(buyerUser);

        User traderUser = User.createNewUser("trader1", "!trader1234", passwordEncoder);
        adminUser.updateRole(UserRole.TRADER);
        userRepository.save(traderUser);

        User noneUser = User.createNewUser("none1", "!none1234", passwordEncoder);
        adminUser.updateRole(UserRole.NONE);
        userRepository.save(noneUser);
    }
}