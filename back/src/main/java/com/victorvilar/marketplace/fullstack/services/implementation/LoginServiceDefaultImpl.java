package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.domain.JwtClaims;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.LoginDTO;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.interfaces.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceDefaultImpl implements LoginService {

    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public LoginServiceDefaultImpl(JwtService jwtService, UserServiceDefaultImpl userServiceDefaultImpl, AuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @Override
    public String login(LoginDTO login) {

        //se não encontrar o spring lança erro automaticamente
        var auth = new UsernamePasswordAuthenticationToken(login.username(),login.password());

        //Tenta realizar o login com os dados passados
        Authentication authentication = authManager.authenticate(auth);

        //Pega o usuario e cria os claims que vou querer adicionar ao jwt
        User user = (User) authentication.getPrincipal();
        Map<String, String> claims = new HashMap<>();
        addClaims(claims, user);

        //cria o token
        return jwtService.generateKey(user.getUsername(),"dados",claims);

    }

     private void addClaims(Map<String, String> claims, User user){
        claims.put(JwtClaims.AUTHORITIES, user.getAuthorities().toString());
        claims.put(JwtClaims.NAME,user.getName());
        claims.put(JwtClaims.PHONE,user.getPhoneNumber());
        claims.put(JwtClaims.ID,user.getId().toString());
    }
}
