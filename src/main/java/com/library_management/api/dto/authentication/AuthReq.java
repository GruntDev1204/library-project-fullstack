package com.library_management.api.dto.authentication;

import jakarta.validation.constraints.NotBlank;
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
}
