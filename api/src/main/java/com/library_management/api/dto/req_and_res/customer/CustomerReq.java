package com.library_management.api.dto.req_and_res.customer;

import jakarta.validation.constraints.NotBlank;
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
    String password;

    @NotBlank(message = "fullName cannot be empty")
    String fullName;

    String phoneNumber;

    @NotBlank(message = "fullName cannot be empty")
    String email;
}
