package com.angerasilas.petroflow_backend.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angerasilas.petroflow_backend.dto.JwtResponse;
import com.angerasilas.petroflow_backend.dto.LoginRequest;
import com.angerasilas.petroflow_backend.dto.PasswordVerificationRequest;
import com.angerasilas.petroflow_backend.dto.ResetPassword;
import com.angerasilas.petroflow_backend.dto.UpdatePasswordDTO;
import com.angerasilas.petroflow_backend.dto.UserDto;
import com.angerasilas.petroflow_backend.exception.ErrorResponse;
import com.angerasilas.petroflow_backend.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    // add User
    @PostMapping("/add")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.createUser(userDto);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // update User
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(id, userDto);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // delete User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //delete users by username

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllUsers(@RequestBody List<String> usernames) {
        userService.deleteAll(usernames);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // get User
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserDto userDto = userService.getUserById(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // get by username
    @GetMapping("/get/username/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable("username") String username) {
        UserDto userDto = userService.getUserByUsername(username);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // get all Users
    @GetMapping("/get/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersData = userService.getAllUsers();

        return ResponseEntity.ok(usersData);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            JwtResponse jwtResponse = userService.login(request.getUsername(), request.getPassword());

            // Return the JWT token directly in the response body
            return ResponseEntity.ok(jwtResponse);
        } catch (IllegalArgumentException e) {
            // Handle invalid username/password or other argument errors
            System.out.println("Invalid username or password: \n" + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Invalid username or password"));
        } catch (Exception e) {
            // Handle general exceptions
            System.out.println("An unexpected error occurred: \n" + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("An unexpected error occurred"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Delete the cookie
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

    // save all Users
    @PostMapping("/add/all")
    public ResponseEntity<List<UserDto>> saveAllUsers(@RequestBody List<UserDto> userDto) {
        List<UserDto> savedUsers = userService.saveAllUsers(userDto);

        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        boolean success = userService.updatePassword(updatePasswordDTO);
        if (success) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Incorrect old password or user not found.");
        }
    }

    // reset password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword resetPassword) {
        boolean success = userService.resetPassword(resetPassword);
        if (success) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    // update users
    @PutMapping("/update/all")
    public ResponseEntity<List<UserDto>> updateUsers(@RequestBody List<UserDto> userDtos) {
        List<UserDto> updatedUsers = userService.updateUsers(userDtos);

        return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
    }

    @PostMapping("/verify-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody PasswordVerificationRequest request) {
        boolean isPasswordValid = userService.verifyUserPassword(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(isPasswordValid);
    }

}
