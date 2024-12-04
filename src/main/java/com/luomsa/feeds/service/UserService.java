package com.luomsa.feeds.service;

import com.luomsa.feeds.dto.UserDto;
import com.luomsa.feeds.entity.User;
import com.luomsa.feeds.repository.UserRepository;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository contextRepository;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, SecurityContextRepository contextRepository, HttpServletRequest request, HttpServletResponse response, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.contextRepository = contextRepository;
        this.request = request;
        this.response = response;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDto> login(String username, String password) {
        var userOptional = userRepository.findByUsernameIgnoreCase(username);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        contextRepository.saveContext(context, request, response);
        var user = userOptional.get();
        return Optional.of(new UserDto(user.getId(), user.getUsername()));
    }

    public Optional<UserDto> register(String username, String password) {
        var userOptional = userRepository.findByUsernameIgnoreCase(username);
        if (userOptional.isPresent()) {
            return Optional.empty();
        }
        var hashedPassword = passwordEncoder.encode(password);
        var user = new User(username, hashedPassword);
        userRepository.save(user);
        return Optional.of(new UserDto(user.getId(), user.getUsername()));
    }

    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).map(user -> new UserDto(user.getId(), user.getUsername()));
    }
}
