package com.victorvilar.marketplace.fullstack.services.implementation;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.exceptions.JobNotFoundException;
import com.victorvilar.marketplace.fullstack.mappers.JobMapper;
import com.victorvilar.marketplace.fullstack.repositories.JobRepository;
import com.victorvilar.marketplace.fullstack.services.interfaces.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobServiceDefaultImpl implements JobService {

    private final JobMapper mapper;
    private final JobRepository repository;
    private static final String JOB_NOT_FOUND = "O serviço não foi encontrado !";

    @Autowired
    public JobServiceDefaultImpl(JobMapper mapper, JobRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<JobDTO> getAll() {
        return this.repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<JobDTO> getByActive(boolean active){
        return this.repository.findByActive(active).stream().map(mapper::toDto).toList();
    }

    @Override
    public JobDTO getById(UUID id) {
        Job job = repository.findById(id).orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND));
        return mapper.toDto(job);
    }

    private Job getByIdNoMap(UUID id){
        return repository.findById(id).orElseThrow(() -> new JobNotFoundException(JOB_NOT_FOUND));
    }

    @Override
    public List<JobDTO> getByProvider(UUID providerId) {
        return repository.findByProviderId(providerId).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<JobDTO> getByCategory(String categoryName) {
        return repository.findByCategoryName(categoryName).stream().map(mapper::toDto).toList();
    }

    @Override
    public JobDTO save(JobDTO jobDTO) {
        if(jobDTO.getId() != null){
            return update(jobDTO);
        }
        Job job = mapper.toEntity(jobDTO);
        return mapper.toDto(repository.save(job));
    }

    @Override
    public JobDTO update(JobDTO dto) {
        Job job = getByIdNoMap(UUID.fromString(dto.getId()));
        mapper.copy(dto,job);
        return mapper.toDto(repository.save(job));
    }

    @Override
    public void delete(UUID id) {
        Job job = getByIdNoMap(id);
        job.setActive(false);
        repository.save(job);
    }

    @Override
    public JobDTO getByIdWithOrders(UUID id) {
        return this.mapper.toDto(repository.findByIdWithOrders(id).get());

    }
}
