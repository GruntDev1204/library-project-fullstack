package com.library_management.api.controller;

import com.library_management.api.dto.authentication.AuthReq;
import com.library_management.api.helper.code_status.SuccessCode;
import com.library_management.api.dto.authentication.AuthRes;
import com.library_management.api.dto.customer.CustomerReq;
import com.library_management.api.dto.customer.CustomerRes;
import com.library_management.api.helper.response.ApiResponse;
import com.library_management.api.service.InterfaceAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController extends BaseController {
    InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes> sv;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthRes>> login(@Valid @RequestBody AuthReq data) {
        return returnResponseJson(SuccessCode.Authentication_is_ok, sv.login(data));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerRes>> register(@Valid @RequestBody CustomerReq data) {
        return returnResponseJson(SuccessCode.Registration_is_done, sv.register(data));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<CustomerRes>> updateProfile(Authentication auth , @Valid @RequestBody CustomerReq data) throws ParseException, JOSEException {
        return returnResponseJson(SuccessCode.UPDATE_SUCCESS, sv.updateProfile(auth, data));
    }

    @GetMapping("/auth/verify-token")
    public ResponseEntity<ApiResponse<Boolean>> checkToken(Authentication auth){
        return returnResponseJson(SuccessCode.Authentication_is_ok, true);
    }

    @GetMapping("/auth/profile")
    public ResponseEntity<ApiResponse<CustomerRes>> getProfile(Authentication auth) throws ParseException, JOSEException {

        return returnResponseJson(SuccessCode.Authentication_is_ok, sv.getProfile(auth));
    }

    public ResponseEntity<ApiResponse<Void>> logout(String token) {
        return null;
    }
}
