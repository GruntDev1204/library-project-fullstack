package com.library_management.api.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerReq {
    @NotBlank(message = "userName cannot be empty")
    String userName;

    @NotBlank(message = "password cannot be empty")
    @Size(min = 10, message = "Password must be at least 10 characters long")
    String password;

    @NotBlank(message = "fullName cannot be empty")
    String fullName;

    String phoneNumber;

    @NotBlank(message = "email cannot be empty")
    String email;
}
