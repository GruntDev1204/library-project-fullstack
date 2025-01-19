package com.library_management.api.controller;

import com.library_management.api.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AuthControllerInterface<AuthReq, AuthRes, InfoReq, InfoRes , M>{
    ResponseEntity<ApiResponse<AuthRes>> login(AuthReq data);

    ResponseEntity<ApiResponse<InfoRes>> register(InfoReq data);

    ResponseEntity<ApiResponse<M>> updateProfile(M data);

    ResponseEntity<ApiResponse<Boolean>> checkToken(String token);

    ResponseEntity<ApiResponse<M>> getProfile(String username);

    ResponseEntity<ApiResponse<Void>> logout(String token);
}
