package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.UserDetailsDto;
import com.angerasilas.petroflow_backend.entity.User;
import com.angerasilas.petroflow_backend.entity.UserDetails;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.UserDetailsMapper;
import com.angerasilas.petroflow_backend.repository.UserDetailsRepository;
import com.angerasilas.petroflow_backend.repository.UserRepository;
import com.angerasilas.petroflow_backend.service.UserDetailsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;
    
    // add UserDetails
    @Override
    public UserDetailsDto saveUserDetails(UserDetailsDto userDetailsDto) {

        User user = userRepository.findById(userDetailsDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = UserDetailsMapper.mapToUserDetails(userDetailsDto, user);

        UserDetails savedUserDetails = userDetailsRepository.save(userDetails);

        return UserDetailsMapper.mapToUserDetailsDto(savedUserDetails);
    }

    @Override
    public UserDetailsDto getUserDetailsById(Long userId) {
        UserDetails userDetails = userDetailsRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Data not found with id " + userId));

        return UserDetailsMapper.mapToUserDetailsDto(userDetails);
    }

    @Override
    public List<UserDetailsDto> getAllUserDetails() {
        List<UserDetails> userDetails = userDetailsRepository.findAll();

        return userDetails.stream().map(UserDetailsMapper::mapToUserDetailsDto).collect(Collectors.toList());
    }

    @Override
    public UserDetailsDto updateUserDetails(Long id, UserDetailsDto userDetailsDto) {
        UserDetails userDetails = userDetailsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        User user = userRepository.findById(userDetailsDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        userDetails.setFirstname(userDetailsDto.getFirstname());
        userDetails.setMiddlename(userDetailsDto.getMiddlename());
        userDetails.setLastname(userDetailsDto.getLastname());
        userDetails.setGender(userDetailsDto.getGender());
        userDetails.setDateOfBirth(userDetailsDto.getDateOfBirth());
        userDetails.setIdNumber(userDetailsDto.getIdNumber());
        userDetails.setPhoneNumber(userDetailsDto.getPhoneNumber());
        userDetails.setEmail(userDetailsDto.getEmail());
        userDetails.setPhysicalAddress(userDetailsDto.getPhysicalAddress());
        userDetails.setPostalCode(userDetailsDto.getPostalCode());
        userDetails.setCity(userDetailsDto.getCity());
        userDetails.setUser(user);

        UserDetails updatedUserDetails = userDetailsRepository.save(userDetails);

        return UserDetailsMapper.mapToUserDetailsDto(updatedUserDetails);
    }

    @Override
    public void deleteUserDetails(Long userId) {
        UserDetails userDetails = userDetailsRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        userDetailsRepository.delete(userDetails);
    }

    @Override
    public List<UserDetailsDto> saveAllUserDetails(List<UserDetailsDto> userDetailsDto) {
        List<UserDetails> userDetails = userDetailsDto.stream()
                .map(dto -> UserDetailsMapper.mapToUserDetails(dto, userRepository.findByUsername(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"))))
                .collect(Collectors.toList());

        List<UserDetails> savedUserDetails = userDetailsRepository.saveAll(userDetails);

        return savedUserDetails.stream().map(UserDetailsMapper::mapToUserDetailsDto).collect(Collectors.toList());
    }  

    @Override
    public UserDetailsDto getUserDetailsByEmail(String email) {
        UserDetails userDetails = userDetailsRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        return UserDetailsMapper.mapToUserDetailsDto(userDetails);
    }

    @Override
    public UserDetailsDto updateUserProfile(String email, UserDetailsDto userDetailsDto) {
        UserDetails userDetails = userDetailsRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));

        userDetails.setMiddlename(userDetailsDto.getMiddlename());
        userDetails.setGender(userDetailsDto.getGender());
        userDetails.setDateOfBirth(userDetailsDto.getDateOfBirth());
        userDetails.setPhoneNumber(userDetailsDto.getPhoneNumber());
        userDetails.setPhysicalAddress(userDetailsDto.getPhysicalAddress());
        userDetails.setPostalCode(userDetailsDto.getPostalCode());
        userDetails.setCity(userDetailsDto.getCity());

        UserDetails updatedUserDetails = userDetailsRepository.save(userDetails);

        return UserDetailsMapper.mapToUserDetailsDto(updatedUserDetails);
    }
}