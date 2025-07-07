package com.library_management.api.helper.auth_process;

import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESEncoder {
    @Value("${AESKey}")
    private String SECRET_KEY;

    private static byte[] codeBase;

    @PostConstruct
    private void init() {
        codeBase = Base64.getDecoder().decode(SECRET_KEY);
    }

    @NotNull
    private static  ByteBuffer combineIv(String strToEncrypt, Cipher cipher, byte[] ivBytes) throws IllegalBlockSizeException, BadPaddingException {
        byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes());

        ByteBuffer buffer = ByteBuffer.allocate(ivBytes.length + encrypted.length);
        buffer.put(ivBytes);
        buffer.put(encrypted);
        return buffer;
    }

    @NotNull
    private static IvParameterSpec getIV(byte[] ivBytes) {
        return new IvParameterSpec(ivBytes);
    }

    private static  byte[] createIvBytes() {
        return new byte[16];
    }

    private static byte @NotNull [] getBuffer(byte[] data, byte[] ivBytes) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        buffer.get(ivBytes);

        byte[] encrypted = new byte[buffer.remaining()];
        buffer.get(encrypted);
        return encrypted;
    }

    public static String encode(String strToEncrypt) {
        try {
            byte[] ivBytes = createIvBytes();
            new SecureRandom().nextBytes(ivBytes);

            SecretKeySpec key = new SecretKeySpec(codeBase, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key , getIV(ivBytes));

            return Base64.getEncoder().encodeToString(combineIv(strToEncrypt, cipher, ivBytes).array());
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException("Error encrypting secret key", e);
        }
    }

    public static String decode(String encryptedStr) {
        try {
            byte[] ivBytes = createIvBytes();

            byte[] data = Base64.getDecoder().decode(encryptedStr);
            final byte[] encrypted = getBuffer(data, ivBytes);

            SecretKeySpec key = new SecretKeySpec(codeBase, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key , getIV(ivBytes));

            return new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException("Error decrypting secret key", e);
        }
    }
}