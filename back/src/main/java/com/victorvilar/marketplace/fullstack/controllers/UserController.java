package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;


    @Autowired
    public UserController(UserService service){
        this.service = service;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable("id") String id){
        UserDTO dto = service.getById(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("/with-jobs/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserWithJobs(@PathVariable("id")String id){
        UserDTO dto = service.getByIdWithJobs(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @GetMapping("/with-orders/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserWithOrders(@PathVariable("id") String id){
        UserDTO dto = service.getByIdWithOrders(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@Valid @RequestBody UserDTO dtoToUpdate){
        UserDTO dto = service.update(dtoToUpdate);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
