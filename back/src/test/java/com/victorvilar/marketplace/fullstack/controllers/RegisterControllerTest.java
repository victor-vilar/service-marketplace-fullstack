package com.victorvilar.marketplace.fullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorvilar.marketplace.fullstack.configuration.SecurityConfiguration;
import com.victorvilar.marketplace.fullstack.dtos.RegisterDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(controllers = RegisterController.class)
@Import(SecurityConfiguration.class)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserService service;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository repository;

    @Test
    public void registerDeveCadastrarUmNovoUsuarioComSucesso() throws Exception{
        RegisterDTO register = new RegisterDTO("teste","teste@email.com","123456");
        UserDTO dto = UserDTO.builder().id("ajfiajef").name(register.name()).email(register.email()).build();
        String registerJson = objectMapper.writeValueAsString(register);

        when(service.save(Mockito.any(RegisterDTO.class))).thenReturn(dto);

        mockMvc.perform(
                post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(registerJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.name").value(dto.getName()))
                .andExpect(jsonPath("$.response.email").value(dto.getEmail()));
    }
    @Test
    public void registerDeveRetornarErro500QuandoNaoForPassadoUmRegister()throws Exception{
        String registerJson = "{\"name\":\"teste\",\"email\":\"email@email\"}";
        mockMvc.perform(
                post("/api/register").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(registerJson))
                .andDo(print())
                .andExpect(status().is(500))
                .andExpect(jsonPath("$.response.message").value("O objeto não pode ser construído !"));

    }
}