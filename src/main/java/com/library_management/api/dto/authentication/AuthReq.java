package com.library_management.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthReq {
    @NotBlank(message = "userName cannot be empty")
    String userName;
    @NotBlank(message = "password cannot be empty")
    String password;

    @Pattern(regexp = "^\\d{6}$", message = "OTP must be a 6-digit number")
    String otp;
}
