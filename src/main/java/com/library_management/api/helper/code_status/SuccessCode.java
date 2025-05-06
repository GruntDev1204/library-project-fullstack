package com.library_management.api.helper.code_status;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SuccessCode{
    // Success codes for GET operations
    GET_ALL_SUCCESS(20001, "Successfully retrieved all DATA!", HttpStatus.OK),
    GET_BY_ID_SUCCESS(20002, "Successfully retrieved the requested DATA!", HttpStatus.OK),

    // Success codes for POST/CREATE operations
    CREATE_SUCCESS(20100, "Successfully created the DATA!", HttpStatus.CREATED),

    // Success codes for PUT/UPDATE operations
    UPDATE_SUCCESS(20003, "Successfully updated the DATA!", HttpStatus.OK),

    // Success codes for DELETE operations
    DELETE_SUCCESS(20404, "Successfully deleted the DATA!", HttpStatus.NO_CONTENT),

    API_IS_VAILD(20000, "Api is running...", HttpStatus.OK),

    Registration_is_done(20001, "Registration is done", HttpStatus.CREATED),
    Authentication_is_ok(20000, "Authentication is ok", HttpStatus.OK),
    ;
    Integer code;
    String message;
    HttpStatus status;
}
