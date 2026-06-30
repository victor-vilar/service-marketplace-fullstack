package com.victorvilar.marketplace.fullstack.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victorvilar.marketplace.fullstack.configuration.SecurityConfiguration;
import com.victorvilar.marketplace.fullstack.domain.Category;
import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.exceptions.JobNotFoundException;
import com.victorvilar.marketplace.fullstack.exceptions.UserNotFoundException;
import com.victorvilar.marketplace.fullstack.mappers.JobMapper;
import com.victorvilar.marketplace.fullstack.repositories.JobRepository;
import com.victorvilar.marketplace.fullstack.services.JwtService;
import com.victorvilar.marketplace.fullstack.services.interfaces.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = JobController.class)
@Import(SecurityConfiguration.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private JobMapper mapper;

    @MockitoBean
    private JobService service;

    @MockitoBean
    private JobRepository repository;

    @MockitoBean
    private JwtService jwtService;

    @Test
    public void getByActive_DeveResponderQuandoForPassadoTrue() throws Exception{
        when(service.getByActive(true)).thenReturn(List.of(createJobDTO(),createJobDTO()));
        mockMvc.perform(
                get("/api/jobs/active/true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response",hasSize(2)));
    }

    @Test
    public void getByActive_DeveResponderQuandoForPassadoFalse() throws Exception{
        JobDTO dto1 = createJobDTO();
        JobDTO dto2 = createJobDTO();
        dto1.setActive(false);
        dto2.setActive(false);
        when(service.getByActive(false)).thenReturn(List.of(dto1,dto2));
        mockMvc.perform(
                        get("/api/jobs/active/false")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response",hasSize(2)));
    }

    @Test
    public void getByProvider_deveBuscarOsJobsQuePertencemAUmProvider() throws Exception{
        when(service.getByProvider(any(UUID.class))).thenReturn(List.of(createJobDTO(),createJobDTO()));
        mockMvc.perform(
                        get("/api/jobs/by-provider/" + UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response",hasSize(2)));
    }

    @Test
    public void getById_deveBuscarUmJobPeloId()throws Exception{
        when(service.getById(any(UUID.class))).thenReturn(createJobDTO());
        mockMvc.perform(
                        get("/api/jobs/" + UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.title").value("titulo"))
                .andExpect(jsonPath("$.response.description").value("descrição"))
                .andExpect(jsonPath("$.response.category").value("Mecanica"));
    }
    @Test
    public void getById_deveRetornarUmResponseErrorQuandoNaoEncontrarUmJob() throws Exception{
        when(service.getById(any(UUID.class))).thenThrow(new JobNotFoundException("O serviço não foi encontrado !"));
        mockMvc.perform(
                        get("/api/jobs/" + UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.response.httpStatus").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.response.message").value("O serviço não foi encontrado !"));
    }

    @Test
    public void getByCategory_deveRetornarJobsQuePossuemCategoria() throws Exception{
        when(service.getByCategory(any(String.class))).thenReturn(List.of(createJobDTO(),createJobDTO()));
        mockMvc.perform(
                        get("/api/jobs/by-category/any")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.response",hasSize(2)));
    }


    @Test
    public void save_deveRetornarJobSalvo() throws Exception{
        when(service.save(any(JobDTO.class))).thenReturn(createJobDTO());
        String job = objectMapper.writeValueAsString(createJobDTO());
        mockMvc.perform(
                        post("/api/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario"))
                                .content(job))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.title").value("titulo"))
                .andExpect(jsonPath("$.response.description").value("descrição"))
                .andExpect(jsonPath("$.response.category").value("Mecanica"));
    }

    @Test
    public void update_deveRetornarJObAtualizado() throws Exception{
        when(service.save(any(JobDTO.class))).thenReturn(createJobDTO());
        String job = objectMapper.writeValueAsString(createJobDTO());
        mockMvc.perform(
                        put("/api/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.title").value("titulo"))
                .andExpect(jsonPath("$.response.description").value("descrição"))
                .andExpect(jsonPath("$.response.category").value("Mecanica"));
    }

    @Test
    public void delete_deveRetornarUmaMensagemDeSucesso() throws Exception{
        String id = UUID.randomUUID().toString();
        mockMvc.perform(
                delete("/api/jobs/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Serviço removido com sucesso !"));
    }


    @Test
    public void getWithOrders_deveRetornarJobComOrders() throws Exception{
        when(service.getByIdWithOrders(any(UUID.class))).thenReturn(createJobDTO());
        String id = UUID.randomUUID().toString();
        mockMvc.perform(
                get("/api/jobs/with-orders/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("usuario").roles("usuario")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.title").value("titulo"))
                .andExpect(jsonPath("$.response.description").value("descrição"))
                .andExpect(jsonPath("$.response.category").value("Mecanica"));
    }

    @Test
    public void getWithOrders_DeveLançarJobNotFoundExceptionQuandoNaoEncontrarOJOB() throws Exception{
        when(service.getByIdWithOrders(any(UUID.class))).thenThrow(new JobNotFoundException("O serviço não foi encontrado !"));
        String id = UUID.randomUUID().toString();
        mockMvc.perform(
                        get("/api/jobs/with-orders/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("usuario").roles("usuario")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.response.httpStatus").value(404))
                .andExpect(jsonPath("$.response.message").value("O serviço não foi encontrado !"));
    }


    private Job createJob(){
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setTitle("titulo");
        job.setDescription("descrição");
        job.setCategory(new Category("Mecanica"));
        job.setPrice(BigDecimal.TEN);
        job.setProvider(new User());
        return job;
    }

    private JobDTO createJobDTO(){
        return new JobDTO().builder()
                .id("id")
                .active(true)
                .title("titulo")
                .description("descrição")
                .category("Mecanica")
                .price(BigDecimal.TEN)
                .provider("provedor")
                .build();
    }

}