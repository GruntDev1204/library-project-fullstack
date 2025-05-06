package com.library_management.api.service;

import com.nimbusds.jose.JOSEException;
import org.springframework.security.core.Authentication;

import java.text.ParseException;

public interface InterfaceService<Req , Res> {
    Res findById(Long id);

    Res create(Req req) throws ParseException, JOSEException;

    Boolean delete(Long id);

    Res update(Long id, Req newReq) throws ParseException, JOSEException;
}
