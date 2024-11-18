package baekgwa.auctionservice.global.config;

import baekgwa.auctionservice.global.security.CustomAccessDeniedHandler;
import baekgwa.auctionservice.global.security.CustomAuthenticationEntryPoint;
import baekgwa.auctionservice.global.security.CustomUserDetailService;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Arrays.asList(
                    "*"
            ));
            config.setAllowCredentials(true);
            config.addExposedHeader("*");
            return config;
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(customUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role("ADMIN").implies("TRADER")
                .role("TRADER").implies("BUYER")
                .role("BUYER").implies("NONE")
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(corsConfigurer -> corsConfigurer.configurationSource(
                        corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error/**", "/favicon.ico/**").permitAll()
                        .requestMatchers("/api/v1/login", "/api/v1/signup").permitAll()
                        .requestMatchers("/docs/index.html").permitAll()
                        .anyRequest().authenticated());

        http
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .csrf(AbstractHttpConfigurer::disable);
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));

        http
                .sessionManagement(auth -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation().changeSessionId()
                        .maximumSessions(1) //로그인 최대 허용치
                        .maxSessionsPreventsLogin(false)); //로그인 허용치 초과시 새로운 로그인 차단

        http
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }
}
