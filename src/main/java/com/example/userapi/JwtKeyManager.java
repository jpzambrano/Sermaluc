package com.example.userapi;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class JwtKeyManager {
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String getEncodedKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }
}