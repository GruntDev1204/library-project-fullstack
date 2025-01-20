package com.library_management.api.service;

import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface InterfaceAuthService<AuthReq, AuthRes, InfoReq, InfoRes, M> {
    InfoRes register(InfoReq data);

    AuthRes login(AuthReq data);

    InfoRes getProfile(String token) throws JOSEException, ParseException;

    M updateProfile(M data);

    Boolean checkToken(String token) throws JOSEException, ParseException;

    Void logout(String token) throws JOSEException, ParseException;
}
