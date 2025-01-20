package com.library_management.api.controller.impl;

import com.library_management.api.controller.AuthControllerInterface;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.code_status.impl.SuccessCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.dto.req_and_res.authentication.AuthReq;
import com.library_management.api.dto.req_and_res.authentication.AuthRes;
import com.library_management.api.dto.req_and_res.customer.CustomerReq;
import com.library_management.api.dto.req_and_res.customer.CustomerRes;
import com.library_management.api.dto.response.ApiResponse;
import com.library_management.api.dto.response.HandleResponseData;
import com.library_management.api.model.Customer;
import com.library_management.api.service.InterfaceAuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController implements AuthControllerInterface<AuthReq, AuthRes, CustomerReq, CustomerRes, Customer> {
    InterfaceAuthService<AuthReq, AuthRes, CustomerReq, CustomerRes, Customer> sv;
    HandleResponseData res;

    @Override
    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthRes>> login(@Valid @RequestBody AuthReq data) {
        return res.returnResponseJson(SuccessCode.Authentication_is_ok, sv.login(data));
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerRes>> register(@Valid @RequestBody CustomerReq data) {
        return res.returnResponseJson(SuccessCode.Registration_is_done, sv.register(data));
    }

    @Override
    public ResponseEntity<ApiResponse<Customer>> updateProfile(Customer data) {
        return null;
    }

    @Override
    @GetMapping("/auth/verify-token")
    public ResponseEntity<ApiResponse<Boolean>> checkToken(@RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Boolean isValid = sv.checkToken(token);
        if (!isValid) throw new ApiException(ErrorCode.Authentication_is_not_ok);

        return res.returnResponseJson(SuccessCode.Authentication_is_ok, true);
    }

    @Override
    @GetMapping("/auth/profile")
    public ResponseEntity<ApiResponse<CustomerRes>> getProfile(@RequestHeader("Authorization") String token) throws ParseException, JOSEException {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return res.returnResponseJson(SuccessCode.Authentication_is_ok, sv.getProfile(token));
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> logout(String token) {
        return null;
    }

}
