package com.victorvilar.marketplace.fullstack.services.interfaces;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;

import java.util.List;
import java.util.UUID;

public interface JobService {

    public List<JobDTO> getAll();
    public JobDTO getById(UUID id);
    public List<JobDTO> getByProvider(UUID providerId);
    public List<JobDTO> getByCategory(String categoryName);
    public JobDTO update(JobDTO job);
    public void delete(UUID id);
    public JobDTO getByIdWithOrders(UUID id);
}
