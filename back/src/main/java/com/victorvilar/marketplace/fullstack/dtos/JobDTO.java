package com.victorvilar.marketplace.fullstack.dtos;

import com.victorvilar.marketplace.fullstack.domain.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record JobDTO(UUID id, String title, String description, BigDecimal price, String category, UUID provider , List<OrderDTO> orders) {

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private UUID id = null;
        private String title;
        private String description;
        private BigDecimal price = BigDecimal.ZERO;
        private String category = "";
        private UUID provider;
        private List<OrderDTO> orders = new ArrayList<>();

        public Builder id(UUID id){
            this.id = id;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder description(String description){
            this.description = description;
            return this;
        }

        public Builder category(String category){
            this.category = category;
            return this;
        }

        public Builder provider(UUID provider){
            this.provider = provider;
            return this;
        }

        public Builder orders(List<OrderDTO> orders){
            this.orders = orders;
            return this;
        }

        public JobDTO build(){

            if(this.title == null || this.description == null || this.provider == null){
                throw new IllegalStateException("Um job deve ter um titulo, descrição e provedor");
            }
            return new JobDTO(id,title,description,price,category,provider,orders);
        }

    }
}
