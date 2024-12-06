package com.luomsa.feeds.controller;

import com.luomsa.feeds.dto.UserDto;
import com.luomsa.feeds.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/me")
    public ResponseEntity<UserDto> me() {
        var user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isEmpty()) {
            var problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
            problem.setTitle("Unauthorized");
            problem.setDetail("Unauthorized");
            return ResponseEntity.of(problem).build();
        }
        return ResponseEntity.ok(user.get());
    }
}
