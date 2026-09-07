package com.interview.product_management.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotNull(message = "Email cannot be null!")
        @Email
        String email,

        @NotNull(message = "Password cannot be null!")
        @Size(min = 8, max = 20, message = "Password must be 8-20 characters long!")
        String password,

        @NotNull(message = "Confirm password cannot be null!")
        String confirmPassword
) {
    public boolean passwordsMatch(){
        return password.equals(confirmPassword);
    }
}
