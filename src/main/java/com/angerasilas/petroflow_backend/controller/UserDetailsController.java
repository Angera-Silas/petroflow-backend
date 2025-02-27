package com.angerasilas.petroflow_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angerasilas.petroflow_backend.dto.UserDetailsDto;
import com.angerasilas.petroflow_backend.service.UserDetailsService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@AllArgsConstructor
@RestController
@RequestMapping("/api/profiles")
public class UserDetailsController {

    private UserDetailsService userDetailsService;

    @PostMapping("/create")
    public ResponseEntity<UserDetailsDto> createUser(@RequestBody UserDetailsDto userDetailsDto) {
        // UserDto savedUser = userService.createUser(userDto);
        UserDetailsDto savedUserDetails = userDetailsService.saveUserDetails(userDetailsDto);

        return new ResponseEntity<>(savedUserDetails, HttpStatus.CREATED);
    }

    // /api/profiles/get/{userId}
    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable("userId") Long userId) {
        {
            UserDetailsDto userDetailsDto = userDetailsService.getUserDetailsById(userId);
            return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
        }
    }

    //get by email
    @GetMapping("/get/email/{email}")
    public ResponseEntity<UserDetailsDto> getUserDetailsByEmail(@PathVariable("email") String email) {
        UserDetailsDto userDetailsDto = userDetailsService.getUserDetailsByEmail(email);
        return new ResponseEntity<>(userDetailsDto, HttpStatus.OK);
    }

    // /api/profiles/get/all
    @GetMapping("/get/all")
    public ResponseEntity<List<UserDetailsDto>> getAllUserDetails() {
        List<UserDetailsDto> usersData = userDetailsService.getAllUserDetails();
        return ResponseEntity.ok(usersData);
    }
    // /api/profiles/update/{userId}
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDetailsDto> updateUserDetails(@PathVariable("userId") Long userId,
            @RequestBody UserDetailsDto userDetailsDto) {
        UserDetailsDto updatedUserDetails = userDetailsService.updateUserDetails(userId, userDetailsDto);
        return new ResponseEntity<>(updatedUserDetails, HttpStatus.OK);
    }
    // /api/profiles/delete/{userId}
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable("userId") Long userId) {
        userDetailsService.deleteUserDetails(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // /api/profiles/add/all
    @PostMapping("/create/all")
    public ResponseEntity<List<UserDetailsDto>> saveAllUserDetails(@RequestBody List<UserDetailsDto> userDetailsDto) {
        List<UserDetailsDto> savedUserDetails = userDetailsService.saveAllUserDetails(userDetailsDto);
        return new ResponseEntity<>(savedUserDetails, HttpStatus.CREATED);
    }

    // /api/profiles/update/all
    @PutMapping("/update/all")
    public ResponseEntity<List<UserDetailsDto>> updateUsers(@RequestBody List<UserDetailsDto> userDetailsDtos) {
        List<UserDetailsDto> updatedUsers = userDetailsService.updateUsers(userDetailsDtos);
        return new ResponseEntity<>(updatedUsers, HttpStatus.OK);
    }

    
    
}
