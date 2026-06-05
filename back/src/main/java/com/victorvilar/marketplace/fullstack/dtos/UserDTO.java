package com.victorvilar.marketplace.fullstack.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UserDTO(String id, String name, String email, String phoneNumber, List<JobDTO> jobs,List<OrderDTO> orders) {

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String id = null;
        private String name;
        private String email;
        private String phoneNumber = "";
        private List<JobDTO> jobs = new ArrayList<>();
        private List<OrderDTO> orders = new ArrayList<>();

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder jobs(List<JobDTO> jobs){
            this.jobs = jobs;
            return this;
        }

        public Builder orders(List<OrderDTO>orders){
            this.orders = orders;
            return this;
        }

        public UserDTO build(){

            if(this.name == null || this.email == null){
                throw new IllegalStateException("Um usuario deve possuir um nome, email e id");
            }

            return new UserDTO(id,name,email,phoneNumber,jobs,orders);

        }


    }

}
