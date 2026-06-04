package com.victorvilar.marketplace.fullstack.dtos;

import com.victorvilar.marketplace.fullstack.domain.Payment;
import com.victorvilar.marketplace.fullstack.domain.Review;
import com.victorvilar.marketplace.fullstack.enums.OrderStatus;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OrderDTO (UUID id, LocalDate creationDate, String orderStatus, BigDecimal totalAmount, List<Review> reviews, String observation, Payment payment, UUID customer, UUID job){

    public static JobDTO.Builder builder(){
        return new JobDTO.Builder();
    }

    public static class Builder{
        private UUID id = null;
        private LocalDate creationDate;
        private String orderStatus = "";
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private List<Review> reviews = new ArrayList();
        private String observation = "";
        private Payment payment = null;
        private UUID customer;
        private UUID job;

        public Builder id(UUID id){
            this.id = id;
            return this;
        }

        public Builder creationDate(LocalDate date){
            this.creationDate = date;
            return this;
        }

        public Builder orderStatus(String orderStatus){
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder totalAmount(BigDecimal amount){
            this.totalAmount = amount;
            return this;
        }

        public Builder reviews(List<Review> reviews ){
            this.reviews = reviews;
            return this;
        }

        public Builder observation(String observation){
            this.observation = observation;
            return this;
        }

        public Builder payment(Payment payment){
            this.payment = payment;
            return this;
        }

        public Builder customer(UUID customer){
            this.customer = customer;
            return this;
        }

        public Builder job(UUID job){
            this.job = job;
            return this;
        }

        public OrderDTO build(){

            if(customer == null || job == null || creationDate == null){
                throw new IllegalStateException("Uma order deve ter um cliente, um serviço e uma data de criação");
            }

            return new OrderDTO(id,creationDate,orderStatus,totalAmount,reviews,observation,payment,customer,job);

        }


    }
}
