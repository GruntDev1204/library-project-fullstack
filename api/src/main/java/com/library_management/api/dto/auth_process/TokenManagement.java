package com.library_management.api.dto.auth_process;

import com.library_management.api.config.EnvClass;
import com.library_management.api.dto.code_status.impl.ErrorCode;
import com.library_management.api.dto.exception.ApiException;
import com.library_management.api.model.Account;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenManagement {
    EnvClass env;

    @NotNull
    public SignedJWT analyzeToken(String token) throws ParseException {
        return SignedJWT.parse(token);
    }

    @NotNull
    public String createToken(Account user) {
        try {
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT)
                    .build();

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUserName())
                    .issuer("library_management_system")
                    .issueTime(Date.from(Instant.now()))
                    .expirationTime(Date.from(Instant.now().plus(24, ChronoUnit.HOURS)))
                    .claim("status", user.getStatus())
                    .build();

            JWSObject jwsObject = new JWSObject(header, new Payload(claims.toJSONObject()));
            jwsObject.sign(new MACSigner(env.getSignerKey().getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new ApiException(ErrorCode.Token_Creation_Failed);
        }
    }

    @NotNull
    public Boolean checkToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(env.getSignerKey().getBytes());
        SignedJWT signedJWT = this.analyzeToken(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return verified && expiryTime.after(new Date());
    }
}
