package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.UserDto;
import com.angerasilas.petroflow_backend.entity.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            null, // Avoid exposing passwords in the DTO
            user.getRole(),
            user.isActive()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
            userDto.getId(),
            userDto.getUsername(),
            encoder.encode(userDto.getPassword()), // Hash password before mapping
            userDto.getRole(),
            userDto.isActive(),
            null,
            null,
            null
        );
    }
}

