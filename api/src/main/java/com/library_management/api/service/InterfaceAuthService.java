package com.library_management.api.service;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;

import java.text.ParseException;

public interface InterfaceAuthService<AuthReq, AuthRes, InfoReq, InfoRes> {
    InfoRes register(InfoReq data);

    AuthRes login(AuthReq data);

    InfoRes getProfile(Authentication auth) throws JOSEException, ParseException;

    InfoRes updateProfile(Authentication auth ,InfoReq data) throws ParseException, JOSEException;

    Void logout(String token) throws JOSEException, ParseException;
}
