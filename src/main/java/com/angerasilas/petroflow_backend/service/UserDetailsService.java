package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.UserDetailsDto;

public interface UserDetailsService {
    
    // add UserDetails
    UserDetailsDto saveUserDetails(UserDetailsDto userDetailsDto);
    
    //get user details by userId

    UserDetailsDto getUserDetailsById(Long userId);


    UserDetailsDto getUserDetailsByEmail(String email);

    //get all userDetails

    List <UserDetailsDto> getAllUserDetails();

    //update user details

    UserDetailsDto updateUserDetails(Long userId, UserDetailsDto userDetailsDto);


    UserDetailsDto updateUserProfile(String email, UserDetailsDto userDetailsDto);

    // delete user details

    void deleteUserDetails(Long userId);

    // add multiple user details
    List<UserDetailsDto> saveAllUserDetails(List<UserDetailsDto> userDetailsDto);

    //update users
    List<UserDetailsDto> updateUsers(List<UserDetailsDto> userDetailsDtos);

}
