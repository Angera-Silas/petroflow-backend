package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.UserDetailsDto;
import com.angerasilas.petroflow_backend.entity.User;
import com.angerasilas.petroflow_backend.entity.UserDetails;

public class UserDetailsMapper {
    

    public static UserDetailsDto mapToUserDetailsDto(UserDetails userDetails) {
        return new UserDetailsDto(
            userDetails.getId(),
            userDetails.getFirstname(),
            userDetails.getMiddlename(),
            userDetails.getLastname(),
            userDetails.getIdNumber(),
            userDetails.getDateOfBirth(),
            userDetails.getGender(),
            userDetails.getEmail(),
            userDetails.getPhoneNumber(),
            userDetails.getPhysicalAddress(),
            userDetails.getPostalCode(),
            userDetails.getCity(),
            userDetails.getRegistrationDate(),
            userDetails.getUser().getId()
        );
    }

    public static UserDetails mapToUserDetails(UserDetailsDto userDetailsDto, User user) {
        return new UserDetails(
            userDetailsDto.getId(),
            userDetailsDto.getFirstname(),
            userDetailsDto.getMiddlename(),
            userDetailsDto.getLastname(),
            userDetailsDto.getIdNumber(),
            userDetailsDto.getDateOfBirth(),
            userDetailsDto.getGender(),
            userDetailsDto.getEmail(),
            userDetailsDto.getPhoneNumber(),
            userDetailsDto.getPhysicalAddress(),
            userDetailsDto.getPostalCode(),
            userDetailsDto.getCity(),
            userDetailsDto.getRegistrationDate(),
            user
        );
    }
}
