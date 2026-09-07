package com.interview.product_management.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @NotBlank(message = "Email cannot be Blank!")
        @Email
        String email,

        @NotBlank(message = "Password cannot be Blank!")
        @Size(min = 8, max = 20, message = "Password must be 8-20 characters long!")
        String password
) {
}
