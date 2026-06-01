package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.implementation.UserServiceDefaultImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {


    private final JwtService jwtService;
    private final UserServiceDefaultImpl userServiceDefaultImpl;

    @Autowired
    public LoginController(JwtService jwtService, UserServiceDefaultImpl userServiceDefaultImpl){
        this.jwtService = jwtService;
        this.userServiceDefaultImpl = userServiceDefaultImpl;
    }

    @GetMapping
    public String login(){
        return "logado";
    }


}
