package com.victorvilar.marketplace.fullstack.controllers;

import com.victorvilar.marketplace.fullstack.domain.ApiResponse;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.services.interfaces.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService service;
    private static final String DELETE_MESSAGE = "Serviço removido com sucesso !";

    @Autowired
    public JobController(JobService service){
        this.service = service;
    }

    @GetMapping("/active/{active}")
    public ResponseEntity<ApiResponse<List<JobDTO>>> getByActivation(@PathVariable("active") boolean active){
        List<JobDTO> jobs = service.getByActive(active);
        return ResponseEntity.ok(ApiResponse.success(jobs));
    }

    @GetMapping("/by-provider/{providerId}")
    public ResponseEntity<ApiResponse<List<JobDTO>>> getByProvider(@PathVariable("providerId") String providerId){
        List<JobDTO> jobs = service.getByProvider(UUID.fromString(providerId));
        return ResponseEntity.ok(ApiResponse.success(jobs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDTO>> getById(@PathVariable("id") String id){
        JobDTO job = service.getById(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success(job));
    }

    @GetMapping("/by-category/{categoryName}")
    public ResponseEntity<ApiResponse<List<JobDTO>>> getByCategoryName(@PathVariable("categoryName")String categoryName){
        List<JobDTO> jobs = service.getByCategory(categoryName);
        return ResponseEntity.ok(ApiResponse.success(jobs));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobDTO>> save(@RequestBody JobDTO dto){
        JobDTO saved = service.save(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/api/jobs/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(ApiResponse.success(saved));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<JobDTO>> update(@RequestBody JobDTO dto){
        JobDTO saved = service.update(dto);
        return ResponseEntity.ok(ApiResponse.success(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") String id){
        service.delete(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success().build(DELETE_MESSAGE));
    }

    @GetMapping("/with-orders/{id}")
    public ResponseEntity<ApiResponse<JobDTO>> getWithOrders(@PathVariable("id") String id){
        JobDTO job = service.getByIdWithOrders(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.success(job));
    }

}
