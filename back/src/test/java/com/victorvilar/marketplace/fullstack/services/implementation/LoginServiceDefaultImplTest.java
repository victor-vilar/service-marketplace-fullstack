package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.domain.JwtClaims;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.LoginDTO;
import com.victorvilar.marketplace.fullstack.enums.TipoUsuario;
import com.victorvilar.marketplace.fullstack.services.JwtService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceDefaultImplTest {

    @InjectMocks
    private LoginServiceDefaultImpl service;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager manager;

    @Captor
    private ArgumentCaptor<Map<String, String>> claimsCaptor;


    Authentication authentication;
    User user;
    LoginDTO loginDto;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(UUID.randomUUID());
        user.setName("teste");
        user.setPhoneNumber("12345");
        user.setEmail("teste@gmail.com");
        user.addRole(TipoUsuario.ADMINISTRADOR);
        loginDto = new LoginDTO("usuario","password");
        authentication = new UsernamePasswordAuthenticationToken(user, "1234");
    }

    @Test
    public void deveGerarOjwtComSucesso(){
        when(manager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtService.generateKey(eq("teste@gmail.com"),eq("dados"),any(HashMap.class))).thenReturn("456123");
        String jwt = service.login(loginDto);
        Assertions.assertEquals(jwt,"456123");
        verify(jwtService,times(1)).generateKey(eq("teste@gmail.com"),eq("dados"),any(HashMap.class));
    }

    @Test
    void verificaSeOsClaimsForamGeradoresCorreatmente(){
        when(manager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(jwtService.generateKey(eq("teste@gmail.com"),eq("dados"),any(HashMap.class))).thenReturn("456123");
        String jwt = service.login(loginDto);
        verify(jwtService,times(1)).generateKey(eq("teste@gmail.com"),eq("dados"),claimsCaptor.capture());
        assertEquals(claimsCaptor.getValue().get(JwtClaims.ID),user.getId().toString());
        assertEquals(claimsCaptor.getValue().get(JwtClaims.NAME),user.getName());
        assertEquals(claimsCaptor.getValue().get(JwtClaims.PHONE),user.getPhoneNumber());
        assertEquals(claimsCaptor.getValue().get(JwtClaims.AUTHORITIES),user.getAuthorities().toString());
    }

}