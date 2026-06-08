package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.LoginDTO;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.implementation.UserServiceDefaultImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    private final JwtService jwtService;
    private final UserServiceDefaultImpl userServiceDefaultImpl;
    private final AuthenticationManager authManager;

    @Autowired
    public LoginController(JwtService jwtService, UserServiceDefaultImpl userServiceDefaultImpl, AuthenticationManager authManager){
        this.jwtService = jwtService;
        this.userServiceDefaultImpl = userServiceDefaultImpl;
        this.authManager = authManager;
    }

    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO login){

        //se não encontrar o spring lança erro automaticamente
        var auth = new UsernamePasswordAuthenticationToken(login.username(),login.password());

        //Tenta realizar o login com os dados passados
        Authentication authentication = authManager.authenticate(auth);

        //cria o token
        var jwt = jwtService.generateKey(authentication.getPrincipal().toString());

        //retorna token no body
        return ResponseEntity.ok().body(jwt);



    }


}
