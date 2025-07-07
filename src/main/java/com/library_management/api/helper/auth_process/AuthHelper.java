package com.library_management.api.helper.auth_process;

import com.library_management.api.helper.code_status.ErrorCode;
import com.library_management.api.exception.ApiException;
import com.library_management.api.model.Account;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthHelper {
    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    private static String KEY;
    private static final Integer defaultSalt = 10;

    @PostConstruct
    public void init() {
        KEY = SIGNER_KEY;
    }

    private static Integer getSalt(Integer optionSalt) {
        return (optionSalt != null) ? optionSalt : defaultSalt;
    }

    @NotNull
    public static String createToken(Account user, String role) {
        try {
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS512)
                    .type(JOSEObjectType.JWT)
                    .build();

            if (!"admin".equalsIgnoreCase(role) && !"customer".equalsIgnoreCase(role)) {
                throw new ApiException(ErrorCode.ROLE_NOT_AVAILABLE);
            }

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUserName())
                    .issuer("library_management_system")
                    .issueTime(Date.from(Instant.now()))
                    .expirationTime(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                    .claim("status", user.getStatus())
                    .claim("role", role)
                    .build();
            JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONObject()));
            jwsObject.sign(new MACSigner(KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new ApiException(ErrorCode.Token_Creation_Failed);
        }
    }

    @NotNull
    public static String getRole(Authentication auth) {
       String role =  auth.getAuthorities().iterator().next().getAuthority();
       if("admin".equalsIgnoreCase(role) || "customer".equalsIgnoreCase(role)) return role;
       else throw new ApiException(ErrorCode.ROLE_NOT_AVAILABLE);
    }

    public static String encodePassword(String password , Integer salt) {
        return new BCryptPasswordEncoder(getSalt(salt)).encode(password);
    }

    public static void verifyPassword(String inputPassword , String basePassword , Integer salt) {
        if(!new BCryptPasswordEncoder(getSalt(salt)).matches(inputPassword , basePassword)) {
            throw new ApiException(ErrorCode.Wrong_Password);
        }
    }
}
