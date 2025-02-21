package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.JwtResponse;
import com.angerasilas.petroflow_backend.dto.ResetPassword;
import com.angerasilas.petroflow_backend.dto.UpdatePasswordDTO;
import com.angerasilas.petroflow_backend.dto.UserDto;
import com.angerasilas.petroflow_backend.entity.User;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.UserMapper;
import com.angerasilas.petroflow_backend.repository.UserRepository;
import com.angerasilas.petroflow_backend.security.JwtUtil;
import com.angerasilas.petroflow_backend.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final JwtUtil jwtUtil; // JWT utility
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // Password encoder

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.mapToUser(userDto);

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

        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());

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

        // Generate token
        String token = jwtUtil.generateToken(user.getUsername());

        return new JwtResponse(token, user.getRole());
    }

    @Override
    public List<UserDto> saveAllUsers(List<UserDto> userDto) {
        List<User> users = userDto.stream().map(UserMapper::mapToUser).collect(Collectors.toList());

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

        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Optional<User> userOptional = userRepository.findByUsername(updatePasswordDTO.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Validate old password
            if (!encoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
                return false; // Old password is incorrect
            }

            // Encrypt the new password and save it
            user.setPassword(encoder.encode(updatePasswordDTO.getNewPassword()));
            userRepository.save(user);
            return true; // Password updated successfully
        }
        return false; // User not found
    }

    @Override
    public boolean resetPassword(ResetPassword reset) {
        Optional<User> userOptional = userRepository.findByUsername(reset.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Encrypt the new password and save it
            user.setPassword(encoder.encode(reset.getNewPassword()));
            userRepository.save(user);
            return true; // Password updated successfully
        }
        return false; // User not found
    }

}
