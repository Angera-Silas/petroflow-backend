package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.UserDto;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import com.angerasilas.petroflow_backend.entity.RoleEntity;
import com.angerasilas.petroflow_backend.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static UserDto mapToUserDto(User user) {
        Set<String> permissions = user.getPermissions().stream()
                .map(PermissionEntity::getName)
                .collect(Collectors.toSet());

        return new UserDto(
            user.getId(),
            user.getUsername(),
            null, // Avoid exposing passwords in the DTO
            user.getRole(), // Return role name to frontend
            user.isActive(),
            permissions
        );
    }

    public static User mapToUser(UserDto userDto, RoleEntity role, Set<PermissionEntity> permissions) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(role.getName()); // Use role name instead of role entity
        user.setRoleEntity(role); // Set RoleEntity reference
        user.setActive(userDto.isActive());
        user.setPermissions(permissions);
        return user;
    }

    public static User mapToUser(UserDto userDto, RoleEntity role) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(role.getName()); // Use role name instead of role entity
        user.setRoleEntity(role); // Set RoleEntity reference
        user.setActive(userDto.isActive());
        return user;
    }
}