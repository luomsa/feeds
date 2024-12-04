package com.luomsa.feeds.service;

import com.luomsa.feeds.dto.UserDto;

import java.util.Optional;

public interface IUserService {
    Optional<UserDto> login(String username, String password);

    Optional<UserDto> register(String username, String password);

    Optional<UserDto> getUserByUsername(String username);
}
