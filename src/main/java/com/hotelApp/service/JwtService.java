package com.hotelApp.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private long expiry;

    private Algorithm algorithm;


    @PostConstruct
    public void postConstructor() throws UnsupportedEncodingException {
        algorithm=Algorithm.HMAC256(algorithmKey);
    }


    public String generateToken(String username) {
        return JWT.create().withClaim("name",username)
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry)).
                withIssuer(issuer).sign(algorithm);
    }

    public String getUsername(String token) {

        DecodedJWT decodedJWT=JWT.require(algorithm)
                .withIssuer(issuer).
                build().verify(token);

        return decodedJWT.getClaim("name").asString();
    }
}
