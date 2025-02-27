package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.JwtResponse;
import com.angerasilas.petroflow_backend.dto.PasswordDto;
import com.angerasilas.petroflow_backend.dto.ResetPassword;
import com.angerasilas.petroflow_backend.dto.UpdatePasswordDTO;
import com.angerasilas.petroflow_backend.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserById(Long userId);

    UserDto getUserByUsername(String username);

    UserDto updateUserByUsername(String username, UserDto userDto);

    UserDto updateUser(Long userId, UserDto userDto);

    List<UserDto> updateUsers(List<UserDto> userDtos);

    void deleteUser(Long userId);

    void deleteAll(List<String> usernames);

    List <UserDto> getAllUsers();

    JwtResponse login(String username, String password);

    List<UserDto> saveAllUsers(List<UserDto> userDto);

    boolean updatePassword(UpdatePasswordDTO updatePasswordDTO);

    boolean resetPassword(ResetPassword reset);

    PasswordDto getUserPassword(String username);

    boolean verifyUserPassword(String username, String inputPassword);

}
