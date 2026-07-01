package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.domain.Job;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.dtos.JobDTO;
import com.victorvilar.marketplace.fullstack.dtos.OrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {


    private final ModelMapper mapper;

    @Autowired
    public OrderMapper(ModelMapper mapper){
        this.mapper = mapper;
    }

    public Order toEntity(OrderDTO dto){
        return this.mapper.map(dto, Order.class);
    }

    public OrderDTO toDto(Order job){
        return mapper.map(job, OrderDTO.class);
    }

    public void copy(OrderDTO src, Order dest){
        mapper.map(src,dest);
    }
}
