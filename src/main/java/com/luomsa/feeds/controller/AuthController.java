package com.luomsa.feeds.controller;

import com.luomsa.feeds.dto.AuthRequestDto;
import com.luomsa.feeds.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDto request) {
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
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequestDto request) {
        var user = userService.register(request.username(), request.password());
        if (user.isEmpty()) {
            var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
            problem.setTitle("Conflict");
            problem.setDetail("Username is already taken");
            return ResponseEntity.of(problem).build();
        }
        return ResponseEntity.ok(user.get());
    }
}
