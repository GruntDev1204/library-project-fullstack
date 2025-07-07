package com.library_management.api.helper.auth_process;

import com.library_management.api.exception.ApiException;
import com.library_management.api.helper.code_status.ErrorCode;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

public class TOTPHelper {
    public static String generateQr(String secretKey ,String userEmail){
        String otpAuthUrl = GoogleAuthenticatorQRGenerator.getOtpAuthURL(
                "Spring_App",
                userEmail,
                new GoogleAuthenticatorKey.Builder(secretKey).build()
        );
        return otpAuthUrl + "&ecc=Q&marker=1&margin=4";
    }

    public static void verifyTOTP(String secretKey, String code) {
        if (!new GoogleAuthenticator().authorize(secretKey, Integer.parseInt(code))) {
            throw new ApiException(ErrorCode.Authentication_is_not_ok);
        }
    }


    public static String generateSecretKey(){
        return new GoogleAuthenticator().createCredentials().getKey();
    }
}
