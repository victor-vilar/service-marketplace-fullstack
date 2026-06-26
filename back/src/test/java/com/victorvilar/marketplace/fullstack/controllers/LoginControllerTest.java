package com.victorvilar.marketplace.fullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorvilar.marketplace.fullstack.configuration.SecurityConfiguration;
import com.victorvilar.marketplace.fullstack.dtos.LoginDTO;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.interfaces.LoginService;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(controllers = LoginController.class)
@Import(SecurityConfiguration.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService service;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository repository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void deveRealizarOLoginConSucesso()throws Exception{
        LoginDTO login = new LoginDTO("teste","teste");
        String json = objectMapper.writeValueAsString(login);
        when(service.login(any(LoginDTO.class))).thenReturn("123456");
        mockMvc.perform(
                post("/api/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso !"))
                .andExpect(jsonPath("$.response").value("123456"));
    }

    @Test
    public void deveLançarBadCredentialsExceptionEmUmErrorQuandoEntrarComLoginErrado()throws Exception{
        LoginDTO login = new LoginDTO("teste","teste");
        String json = objectMapper.writeValueAsString(login);
        when(service.login(any(LoginDTO.class))).thenThrow(new BadCredentialsException("Usuário ou Senha inválidos !"));
        mockMvc.perform(
                        post("/api/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Erro !"))
                .andExpect(jsonPath("$.response.message").value("Usuário ou Senha inválidos !"));
    }

}