package com.library_management.api.dto.exception;

import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandleGlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
//        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .code(500)
                        .message("An unexpected error occurred: " + e.getMessage())
                        .build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();

        if (e.getDetails() != null && !e.getDetails().isEmpty()) {
            return ResponseEntity.status(errorCode.getStatus()).body(
                    ExceptionResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .detail(e.getDetails())
                            .build());
        } else {
            return ResponseEntity.status(errorCode.getStatus()).body(
                    ExceptionResponse.builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .build());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.unprocessableEntity().body(ExceptionResponse.builder()
                .code(ErrorCode.CREATE_FAILED.getCode())
                .message(ErrorCode.CREATE_FAILED.getMessage())
                .detail(errors)
                .build());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException e) {
        String errorMessage = "Database error occurred: " + e.getMessage();
        String sqlState = e.getSQLState();
        int errorCode = e.getErrorCode();
        Throwable cause = e.getCause();

        String detailedMessage = String.format("SQLState: %s, ErrorCode: %d, Message: %s", sqlState, errorCode,
                errorMessage);

        if (cause != null) {
            detailedMessage += ", Cause: " + cause.toString();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ExceptionResponse.builder()
                        .code(500)
                        .message("A database error occurred: " + e.getMessage())
                        .detail(detailedMessage)
                        .build());
    }
}
