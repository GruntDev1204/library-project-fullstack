package com.library_management.api.dto.code_status;

import org.springframework.http.HttpStatus;

public interface CodeResponseInterface {
    Integer getCode();

    String getMessage();

    HttpStatus getStatus();
}