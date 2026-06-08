package com.victorvilar.marketplace.fullstack.configuration;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper
                .typeMap(Job.class,JobDTO.class)
                .addMapping(src-> src.getProvider().getId(),JobDTO::setProvider);

        mapper
                .typeMap(User.class,UserDTO.class)
                .addMappings(src -> src.skip(UserDTO::setPassword));

        return mapper;
    }



}
