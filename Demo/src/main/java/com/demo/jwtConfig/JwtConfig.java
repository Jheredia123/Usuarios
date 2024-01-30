package com.demo.jwtConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

//    @Value("${security.jwt.expiration}")
//    private long expirationTime;
    private long expirationTime = 86400000; //1 día en milisegundos

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
}

