package com.luomsa.feeds.controller;

import com.luomsa.feeds.dto.AuthRequestDto;
import com.luomsa.feeds.dto.UserDto;
import com.luomsa.feeds.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final HttpServletRequest request;

    public AuthController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody AuthRequestDto request) {
        var user = userService.login(request.username(), request.password());
        if (user.isEmpty()) {
            var problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            problem.setTitle("Unauthorized");
            problem.setDetail("Wrong username or password");
            return ResponseEntity.of(problem).build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody AuthRequestDto request) {
        var user = userService.register(request.username(), request.password());
        if (user.isEmpty()) {
            var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
            problem.setTitle("Conflict");
            problem.setDetail("Username is already taken");
            return ResponseEntity.of(problem).build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        request.getSession().invalidate();
        return ResponseEntity.ok().header("Set-Cookie", "JSESSIONID=; Path=/; Max-Age=0; SameSite=Strict; HttpOnly").build();
    }
}
