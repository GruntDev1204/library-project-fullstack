package com.library_management.api.service;

public interface InterfaceAuthService<AuthReq, AuthRes, InfoReq, InfoRes , M> {
    InfoRes register (InfoReq data);
    AuthRes login(AuthReq data);
    M getProfile(String username);
    M updateProfile(M data);
    Boolean checkToken(String token);
}
