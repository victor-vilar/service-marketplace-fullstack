package com.victorvilar.marketplace.fullstack.mappers;

import com.victorvilar.marketplace.fullstack.creators.OrderTestCreator;
import com.victorvilar.marketplace.fullstack.domain.Order;
import com.victorvilar.marketplace.fullstack.dtos.OrderDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    private OrderMapper mapper;

    @Test
    void toEntity_DeveTransformarUmOrderDTOEmUmaOrderComSucesso() {
        OrderDTO dto = OrderTestCreator.criarUmaOrderDTOCompleta();
        Order order = mapper.toEntity(dto);
        Assertions.assertEquals(dto.getOrderStatus(),order.getOrderStatus().toString());
        Assertions.assertEquals(dto.getTotalAmount(),order.getTotalAmount());
        Assertions.assertEquals(dto.getObservation(),order.getObservation());
        Assertions.assertEquals(dto.getPayment().getId().toString(),order.getPayment().getId().toString());
        Assertions.assertEquals(dto.getReviews().size(),order.getReviews().size());
    }

    @Test
    void toDto_DeveTransformarUmaOrderEmUmaOrderDTOComSucesso() {
        Order order = OrderTestCreator.criarUmaOrderCompleta();
        OrderDTO dto = mapper.toDto(order);
        Assertions.assertEquals(dto.getId(),order.getId().toString());
        Assertions.assertEquals(dto.getOrderStatus(),order.getOrderStatus().getStatus());
        Assertions.assertEquals(dto.getTotalAmount(),order.getTotalAmount());
        Assertions.assertEquals(dto.getObservation(),order.getObservation());
        Assertions.assertEquals(dto.getPayment().getId().toString(),order.getPayment().getId().toString());
        Assertions.assertEquals(dto.getReviews().size(),order.getReviews().size());
        Assertions.assertEquals(dto.getJob(),order.getJob().getId().toString());
        Assertions.assertEquals(dto.getCustomer(),order.getCustomer().getId().toString());
    }

    @Test
    void copy_DeveCopiarOsDadosDeUmaOrderDTOParaUmaOrder() {
        OrderDTO dto = OrderTestCreator.criarUmaOrderDTOCompleta();
        Order order = new Order();
        mapper.copy(dto,order);
        Assertions.assertEquals(order.getId(),null);
        Assertions.assertEquals(dto.getOrderStatus(),order.getOrderStatus().toString());
        Assertions.assertEquals(dto.getTotalAmount(),order.getTotalAmount());
        Assertions.assertEquals(dto.getObservation(),order.getObservation());
        Assertions.assertEquals(dto.getPayment().getId().toString(),order.getPayment().getId().toString());
        Assertions.assertEquals(dto.getReviews().size(),order.getReviews().size());
    }
}