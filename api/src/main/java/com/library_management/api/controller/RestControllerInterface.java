package com.library_management.api.controller;

import com.library_management.api.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface RestControllerInterface<Req, Res, R, Td, P> {
    ResponseEntity<ApiResponse<Td>> getAll(R requestParam, P options);

    ResponseEntity<ApiResponse<Res>> findById(Long id);

    ResponseEntity<ApiResponse<Res>> create( Req data);

    ResponseEntity<ApiResponse<Void>> delete(Long id);

    ResponseEntity<ApiResponse<Res>> update(Long id, Req newData);
}