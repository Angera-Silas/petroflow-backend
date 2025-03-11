package com.angerasilas.petroflow_backend.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angerasilas.petroflow_backend.dto.JwtResponse;
import com.angerasilas.petroflow_backend.dto.PasswordDto;
import com.angerasilas.petroflow_backend.dto.ResetPassword;
import com.angerasilas.petroflow_backend.dto.UpdatePasswordDTO;
import com.angerasilas.petroflow_backend.dto.UserDto;
import com.angerasilas.petroflow_backend.dto.UserPermissionsDto;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import com.angerasilas.petroflow_backend.entity.RoleEntity;
import com.angerasilas.petroflow_backend.entity.User;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.UserMapper;
import com.angerasilas.petroflow_backend.repository.PermissionRepository;
import com.angerasilas.petroflow_backend.repository.RoleRepository;
import com.angerasilas.petroflow_backend.repository.UserRepository;
import com.angerasilas.petroflow_backend.security.JwtUtil;
import com.angerasilas.petroflow_backend.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;
    private RoleRepository roleRepository;
    private final JwtUtil jwtUtil; // JWT utility
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Password encoder


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        RoleEntity role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name " + userDto.getRole()));

        Set<PermissionEntity> defaultPermissions = new HashSet<>(role.getPermissions());

        User user = UserMapper.mapToUser(userDto, role, defaultPermissions);

        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());

        RoleEntity role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name " + userDto.getRole()));

        user.setRole(role.getName());
        user.setRoleEntity(role);

        Set<PermissionEntity> permissions = userDto.getPermissions().stream()
                .map(permissionRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        user.setPermissions(permissions);

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public JwtResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username or password"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new JwtResponse(token, user.getRole());
    }

    @Override
    public List<UserDto> saveAllUsers(List<UserDto> userDto) {
        List<User> users = userDto.stream().map(userDtoItem -> {
            Set<PermissionEntity> permissions = userDtoItem.getPermissions().stream()
                    .map(permissionRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            RoleEntity role = roleRepository.findByName(userDtoItem.getRole())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with name " + userDtoItem.getRole()));

            return UserMapper.mapToUser(userDtoItem, role, permissions);
        }).collect(Collectors.toList());

        List<User> savedUsers = userRepository.saveAll(users);

        return savedUsers.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUserByUsername(String username, UserDto userDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());

        RoleEntity role = roleRepository.findByName(userDto.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name " + userDto.getRole()));

        user.setRole(role.getName());
        user.setRoleEntity(role);

        Set<PermissionEntity> permissions = userDto.getPermissions().stream()
                .map(permissionRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        user.setPermissions(permissions);

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Optional<User> userOptional = userRepository.findByUsername(updatePasswordDTO.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!encoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
                return false;
            }

            user.setPassword(encoder.encode(updatePasswordDTO.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(ResetPassword reset) {
        Optional<User> userOptional = userRepository.findByUsername(reset.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            user.setPassword(encoder.encode(reset.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> updateUsers(List<UserDto> userDtos) {
        List<User> users = userDtos.stream().map(userDto -> {
            User user = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userDto.getId()));

            user.setPassword(encoder.encode(userDto.getPassword()));
            user.setUsername(userDto.getUsername());

            RoleEntity role = roleRepository.findByName(userDto.getRole())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with name " + userDto.getRole()));

            user.setRole(role.getName());
            user.setRoleEntity(role);

            Set<PermissionEntity> permissions = userDto.getPermissions().stream()
                    .map(permissionRepository::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            user.setPermissions(permissions);

            return user;
        }).collect(Collectors.toList());

        List<User> updatedUsers = userRepository.saveAll(users);

        return updatedUsers.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public PasswordDto getUserPassword(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        return new PasswordDto(user.getPassword());
    }

    @Override
    public boolean verifyUserPassword(String username, String inputPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        return encoder.matches(inputPassword, user.getPassword());
    }

    @Override
    public void deleteAll(List<String> usernames) {
        List<User> users = userRepository.findByUsernameIn(usernames);

        userRepository.deleteAll(users);
    }

    @Override
    public Set<PermissionEntity> getCombinedPermissions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        RoleEntity roleEntity = roleRepository.findByName(user.getRole())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name " + user.getRole()));

        Set<PermissionEntity> combinedPermissions = new HashSet<>(roleEntity.getPermissions());
        combinedPermissions.addAll(user.getPermissions());

        return combinedPermissions;
    }

    @Override
    public Page<UserPermissionsDto> getAllUserPermissions(Pageable pageable) {
        Page<UserPermissionsDto> usersPage = userRepository.findUsersWithoutPermissions(pageable);

        // Convert Page to List to modify permissions
        List<UserPermissionsDto> users = usersPage.getContent();

        users.forEach(user -> {
            List<String> permissions = permissionRepository.findPermissionsByUserId(user.getUserId());
            user.setPermissions(permissions);
        });

        return new PageImpl<>(users, pageable, usersPage.getTotalElements());
    }

    
    @Override
    public Optional<UserPermissionsDto> getUserPermissionsByUserId(Long userId) {
        Optional<UserPermissionsDto> userPermissionsDto = userRepository.findUserPermissionsByUserId(userId);

        userPermissionsDto.ifPresent(user -> {
            List<String> permissions = permissionRepository.findPermissionsByUserId(user.getUserId());
            user.setPermissions(permissions);
        });

        return userPermissionsDto;
    }

}