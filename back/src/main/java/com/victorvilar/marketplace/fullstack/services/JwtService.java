package com.victorvilar.marketplace.fullstack.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.issuer}")
    private String tokenIssuer;

    public String generateKey(String identification){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(tokenIssuer) // quem emitiu o token
                    .withSubject(identification) // dono do token
                    .withExpiresAt(generateExpiration()) //tempo de validade
                    .sign(algorithm); // assina com achave secreta

        }catch(JWTCreationException e){
            throw new RuntimeException("Erro ao gerar Token");
        }

    }

    public String verifyKey(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer(tokenIssuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    public boolean isTokenExpired(String token){
        return false;
    }

    public Instant generateExpiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
