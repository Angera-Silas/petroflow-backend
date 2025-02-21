package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    private String username;      // The email or username of the user
    private String newPassword;
}
