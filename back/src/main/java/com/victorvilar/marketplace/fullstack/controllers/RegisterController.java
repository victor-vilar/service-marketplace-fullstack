package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.dtos.RegisterDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final UserService service;
    private static final String SUCCESS_MESSAGE =  "Registro realizado com sucesso !";
    private final PasswordEncoder encoder;

    @Autowired
    public RegisterController(UserService service, PasswordEncoder encoder){
        this.service = service;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterDTO register){

        UserDTO user = UserDTO.builder()
                .name(register.name())
                .email(register.email())
                .password(encoder.encode(register.password()))
                .build();

        user = this.service.save(user);
        ApiResponse<UserDTO> response = new ApiResponse<>(LocalDate.now(),user,true,SUCCESS_MESSAGE,null);
        return ResponseEntity.ok(response);
    }



}
