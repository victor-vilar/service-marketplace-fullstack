package com.victorvilar.marketplace.fullstack.dtos;

import java.util.UUID;

public record PaymentDTO(String id, String PaymentStatus, String paymentMethod, String order) {

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        String id = null;
        String paymentStatus;
        String paymentMethod;
        String order;

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder paymentStatus(String paymentStatus){
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder paymentMethod(String paymentMethod){
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder order (String order){
            this.order = order;
            return this;
        }
        
        public PaymentDTO build(){
            return new PaymentDTO(id,paymentStatus,paymentMethod,order);
        }
    }

}
