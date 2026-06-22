package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.domain.User;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final UserService service;
    private static final String SUCCESS_MESSAGE =  "Registro realizado com sucesso !";

    @Autowired
    public RegisterController(UserService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> register(@Valid @RequestBody RegisterDTO register){

        UserDTO user = this.service.save(register);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        ApiResponse<UserDTO> response = ApiResponse.success(SUCCESS_MESSAGE).build(user);
        return ResponseEntity.created(uri).body(response);
    }



}
