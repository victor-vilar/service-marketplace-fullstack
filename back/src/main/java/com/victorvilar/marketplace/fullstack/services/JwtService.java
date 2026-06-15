package com.victorvilar.marketplace.fullstack.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.victorvilar.marketplace.fullstack.domain.JwtClaims;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                    .withExpiresAt(generateExpiration())
                    //tempo de validade
                    .sign(algorithm); // assina com achave secreta

        }catch(JWTCreationException e){
            throw new RuntimeException("Erro ao gerar Token");
        }

    }

    public String generateKey(String identification, String claimName, String claim){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(tokenIssuer) // quem emitiu o token
                    .withSubject(identification) // dono do token
                    .withExpiresAt(generateExpiration())
                    .withClaim(claimName,claim)
                    .sign(algorithm); // assina com achave secreta

        }catch(JWTCreationException e){
            throw new RuntimeException("Erro ao gerar Token");
        }
    }

    public String generateKey(String identification, String claimName, Map<String,String> claims){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer(tokenIssuer) // quem emitiu o token
                    .withSubject(identification) // dono do token
                    .withExpiresAt(generateExpiration())
                    .withClaim(claimName,claims)
                    .sign(algorithm);

        }catch(JWTCreationException e){
            throw new RuntimeException("Erro ao gerar Token");
        }
    }


    public User verifyKey(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT jwt = JWT.require(algorithm)
                    .withIssuer(tokenIssuer)
                    .build()
                    .verify(token);
            return getSubject(jwt);

        } catch (JWTVerificationException exception){
            return null;
        }
    }

    public User getSubject(DecodedJWT jwt){
        User user = new User();
        Map<String, Object> dados = jwt.getClaim("dados").asMap();

        user.setName((String) dados.get(JwtClaims.NAME));
        user.setPhoneNumber((String) dados.get(JwtClaims.PHONE));

        String authorities = (String)dados.get(JwtClaims.AUTHORITIES);
        String autho = authorities.replaceAll("\\[|\\]","").replace(" ","");
        String[] arr = autho.split(",");

        Arrays.asList(arr).stream().forEach(a -> user.addRole(TipoUsuario.valueOf(a)));

        return user;
    }


    public boolean isTokenExpired(String token){
        return false;
    }

    public Instant generateExpiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
