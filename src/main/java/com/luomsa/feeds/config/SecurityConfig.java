package com.luomsa.feeds.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luomsa.feeds.repository.UserRepository;
import com.luomsa.feeds.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import java.net.URI;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityContextRepository contextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .anonymous(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/auth/login", "/api/auth/register", "/api/feeds/", "/error", "v3/api-docs").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts", "/api/posts/{postId}", "/api/posts/{postId}/comments").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .securityContext(context -> context.securityContextRepository(contextRepository()))
                .requestCache(AbstractHttpConfigurer::disable)
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setHeader("Set-Cookie", "JSESSIONID=; Path=/; Max-Age=0; SameSite=Strict; HttpOnly");
                            var problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                            problem.setInstance(URI.create(request.getRequestURI()));
                            response.setStatus(problem.getStatus());
                            response.setContentType("application/problem+json");
                            problem.setTitle("Unauthorized");
                            if (request.getRequestedSessionId() != null) {
                                problem.setDetail("Session expired");
                                objectMapper.writeValue(response.getOutputStream(), problem);
                                return;
                            }
                            problem.setDetail("Unauthorized");
                            objectMapper.writeValue(response.getOutputStream(), problem);
                        })
                )
                .sessionManagement(session -> {
                    session.maximumSessions(1);
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
                });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }
}
