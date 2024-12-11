package com.luomsa.feeds;

import com.luomsa.feeds.entity.User;
import com.luomsa.feeds.repository.UserRepository;
import com.luomsa.feeds.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserByUsername() {
        when(userRepository.findByUsernameIgnoreCase("user")).thenReturn(Optional.of(new User("user", "password")));
        var userOptional = userService.getUserByUsername("user");
        verify(userRepository).findByUsernameIgnoreCase("user");
        var user = userOptional.get();
        assert user.username().equals("user");
    }
}
