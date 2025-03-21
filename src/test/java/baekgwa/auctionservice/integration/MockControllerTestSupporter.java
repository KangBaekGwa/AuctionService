package baekgwa.auctionservice.integration;

import baekgwa.auctionservice.model.user.repository.UserRepository;
import baekgwa.auctionservice.domain.authentication.service.AuthService;
import baekgwa.auctionservice.global.config.SecurityConfig;
import baekgwa.auctionservice.global.security.CustomUserDetailService;
import baekgwa.auctionservice.domain.authentication.controller.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        AuthController.class,
})
@Import({
        SecurityConfig.class,
})
@ActiveProfiles("test")
public abstract class MockControllerTestSupporter {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected CustomUserDetailService customUserDetailService;

    protected static final String basePath = "/api/v1";
}
