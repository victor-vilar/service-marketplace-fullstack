package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.domain.JwtClaims;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.LoginDTO;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.implementation.UserServiceDefaultImpl;
import com.victorvilar.marketplace.fullstack.services.interfaces.LoginService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService service;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String SUCCESSFULL_AUTHENTICATION = "Login realizado com sucesso !";

    @Autowired
    public LoginController(LoginService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginDTO login){
        String jwt = service.login(login);
        logger.info(SUCCESSFULL_AUTHENTICATION);
        return ResponseEntity.ok().body(ApiResponse.success(SUCCESSFULL_AUTHENTICATION).build(jwt));
    }




}
