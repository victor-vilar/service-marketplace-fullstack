package com.victorvilar.marketplace.fullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorvilar.marketplace.fullstack.configuration.SecurityConfiguration;
import com.victorvilar.marketplace.fullstack.dtos.RegisterDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import com.victorvilar.marketplace.fullstack.exceptions.UserNotFoundException;
import com.victorvilar.marketplace.fullstack.repositories.UserRepository;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@Import(SecurityConfiguration.class)
class UserControllerTest {

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
    public void deveBuscarUmUsuarioPorIdComSucesso() throws Exception{
        UserDTO dto = UserDTO.builder().id("ajfiajef").name("teste").email("teste@email.com").build();
        when(service.getById(any(UUID.class))).thenReturn(dto);

        String id = UUID.randomUUID().toString();

        mockMvc.perform(
                get("/api/users/" + id).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.name").value(dto.getName()))
                .andExpect(jsonPath("$.response.email").value(dto.getEmail())
                );

    }

    @Test
    public void deveLançarExceptionAoNaoEncontrarERetornarUmResponseErrorQuandoBuscarPorId()  throws Exception{
        when(service.getById(any(UUID.class))).thenThrow(new UserNotFoundException("Usuário não encontrado !"));
        String id = UUID.randomUUID().toString();

        mockMvc.perform(
                        get("/api/users/" + id).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))

                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Erro !"))
                .andExpect(jsonPath("$.response.httpStatus").value(404))
                .andExpect(jsonPath("$.response.message").value("Usuário não encontrado !")
                );
    }
    @Test
    public void deveBuscarUmUsuarioComSeusJobsPorIdComSucess() throws Exception{
        UserDTO dto = UserDTO.builder().id("ajfiajef").name("teste").email("teste@email.com").build();
        when(service.getByIdWithJobs(any(UUID.class))).thenReturn(dto);

        String id = UUID.randomUUID().toString();

        mockMvc.perform(
                        get("/api/users/with-jobs/" + id).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.name").value(dto.getName()))
                .andExpect(jsonPath("$.response.email").value(dto.getEmail())
                );
    }
    @Test
    public void deveLançarExceptionDeUsuarioNaoEncontradoQuandoNaoEncontarOIdQuandoBuscarJuntoComJobs() throws Exception{
        when(service.getByIdWithJobs(any(UUID.class))).thenThrow(new UserNotFoundException("Usuário não encontrado !"));
        String id = UUID.randomUUID().toString();

        mockMvc.perform(
                        get("/api/users/with-jobs/" + id).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))

                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Erro !"))
                .andExpect(jsonPath("$.response.httpStatus").value(404))
                .andExpect(jsonPath("$.response.message").value("Usuário não encontrado !")
                );
    }
    @Test
    public void deveBuscarUmUsuarioComSuasOrdersPorIdComSucesso() throws Exception{
        UserDTO dto = UserDTO.builder().id("ajfiajef").name("teste").email("teste@email.com").build();
        when(service.getByIdWithOrders(any(UUID.class))).thenReturn(dto);

        String id = UUID.randomUUID().toString();

        mockMvc.perform(
                        get("/api/users/with-orders/" + id).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.name").value(dto.getName()))
                .andExpect(jsonPath("$.response.email").value(dto.getEmail())
                );
    }
    @Test
    public void deveLançarExceptionDeUsuarioNaoEncontradoQuandoNaoEncontrarOIdQuandoBuscarJuntoComAsOrders() throws Exception{
        when(service.getByIdWithOrders(any(UUID.class))).thenThrow(new UserNotFoundException("Usuário não encontrado !"));
        String id = UUID.randomUUID().toString();

        mockMvc.perform(
                        get("/api/users/with-orders/" + id).with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))

                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Erro !"))
                .andExpect(jsonPath("$.response.httpStatus").value(404))
                .andExpect(jsonPath("$.response.message").value("Usuário não encontrado !")
                );
    }

    @Test
    public void deveChamarMetodoUpdateDoServiceERetornarDtoDoObjetoAtualizado() throws Exception{
        UserDTO dto = UserDTO.builder().id("ajfiajef").name("teste").email("teste@email.com").build();
        when(service.update(any(UserDTO.class))).thenReturn(dto);
        String dtoJson = objectMapper.writeValueAsString(dto);

        String id = UUID.randomUUID().toString();
        mockMvc.perform(
                        put("/api/users").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario"))
                                .content(dtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response.name").value(dto.getName()))
                .andExpect(jsonPath("$.response.email").value(dto.getEmail())
                );
    }
    
    @Test
    public void deveLançarUserNotFoundExceptionQuandoNaoForEncontrdoUserComOIdDoDTOPassadoNoMetodoUpdate()throws Exception{
        UserDTO dto = UserDTO.builder().id("ajfiajef").name("teste").email("teste@email.com").build();
        when(service.update(any(UserDTO.class))).thenThrow(new UserNotFoundException("Usuário não encontrado !"));
        String dtoJson = objectMapper.writeValueAsString(dto);
        mockMvc.perform(
                        put("/api/users").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")).content(dtoJson))

                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Erro !"))
                .andExpect(jsonPath("$.response.httpStatus").value(404))
                .andExpect(jsonPath("$.response.message").value("Usuário não encontrado !")
                );
    }

}