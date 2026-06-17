package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobMapper {

    private final ModelMapper mapper;

    @Autowired
    public JobMapper(ModelMapper mapper){
        this.mapper = mapper;
    }

    public Job toEntity(JobDTO dto){
        return this.mapper.map(dto, Job.class);
    }

    public JobDTO toDto(Job user){
        return mapper.map(user, JobDTO.class);
    }

    public void copy(JobDTO src, Job dest){
        mapper.map(src,dest);
    }

}
