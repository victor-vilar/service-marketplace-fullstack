package com.victorvilar.marketplace.fullstack.configuration;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.domain.User;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.dtos.OrderDTO;
import com.victorvilar.marketplace.fullstack.dtos.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();

        /** Configuração de Job*/
        mapper
                .typeMap(Job.class,JobDTO.class)
                .addMapping(src-> src.getProvider().getId(),JobDTO::setProvider);
        mapper
                .typeMap(JobDTO.class, Job.class)
                        .addMappings(src -> src.skip(Job::setId));

        /** Configuração de Order */
        mapper
                .typeMap(Order.class, OrderDTO.class)
                .addMappings(src ->{
                    src.map(maper -> maper.getCustomer().getId(),OrderDTO::setCustomer);
                    src.map(maper -> maper.getJob().getId(),OrderDTO::setJob);
                });

        mapper
                .typeMap(OrderDTO.class, Order.class)
                .addMappings(src -> src.skip(Order::setId));

        /** Configuração de User */
        mapper
                .typeMap(User.class,UserDTO.class)
                .addMappings(src -> src.skip(UserDTO::setPassword));
        mapper
                .typeMap(UserDTO.class, User.class)
                .addMappings(src -> src.skip(User::setId));
        return mapper;
    }



}
