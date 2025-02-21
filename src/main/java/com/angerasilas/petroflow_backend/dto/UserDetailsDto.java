package com.angerasilas.petroflow_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
    private String firstname;

    private String middlename;

    @NotNull(message = "Last name cannot be null")
    private String lastname;

    private String idNumber;

    private String dateOfBirth;

    private String gender;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;

    private String physicalAddress;
    private String postalCode;
    private String city;
    private String registrationDate;

    private Long userId;
}
