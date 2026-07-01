package com.victorvilar.marketplace.fullstack.creators;

import com.victorvilar.marketplace.fullstack.domain.Category;
import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class JobTestCreator {

    public static Job criarJobCompleto(){
        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setProvider(UserTestCreator.criarUserCompleto());
        job.setTitle("Titulo");
        job.setDescription("Descrição");
        job.setPrice(BigDecimal.TEN);
        job.setActive(true);
        job.setCategory(new Category("Mecanica"));
        return job;
    }

    public static JobDTO criarJobDTOCompleto(){
        return JobDTO.builder()
                .id(UUID.randomUUID().toString())
                .provider(UserTestCreator.criarUserCompleto().getId().toString())
                .title("Titulo dto")
                .description("Description dto")
                .price(BigDecimal.TEN)
                .active(true)
                .category(new Category("Mecanica").getName())
                .build();
    }
}
