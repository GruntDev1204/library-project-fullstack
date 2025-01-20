package com.library_management.api.controller;

import com.library_management.api.dto.response.ApiResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface AuthControllerInterface<AuthReq, AuthRes, InfoReq, InfoRes, M> {
    ResponseEntity<ApiResponse<AuthRes>> login(AuthReq data);

    ResponseEntity<ApiResponse<InfoRes>> register(InfoReq data);

    ResponseEntity<ApiResponse<M>> updateProfile(M data);

    ResponseEntity<ApiResponse<Boolean>> checkToken(String token) throws ParseException, JOSEException;

    ResponseEntity<ApiResponse<InfoRes>> getProfile(String token) throws JOSEException, ParseException;

    ResponseEntity<ApiResponse<Void>> logout(String token);
}
