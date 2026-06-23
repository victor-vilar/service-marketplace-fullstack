package com.victorvilar.marketplace.fullstack.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.*;

public class UserDTO{

    private String id = null;
    private String name;
    private String email;
    private String password;
    private String phoneNumber = "";
    private List<JobDTO> jobs;
    private List<OrderDTO> orders;
    private Set<String> authorities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<JobDTO> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDTO> jobs) {
        this.jobs = jobs;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> roles) {
        this.authorities = roles;
    }

    public UserDTO(){

    }

    public UserDTO(String id, @NotNull String name, @NotNull String email, String password, String phoneNumber, List<JobDTO> jobs, List<OrderDTO> orders, Set<String> authorities){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.jobs = jobs;
        this.orders = orders;
        this.authorities = authorities;
    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String id = null;
        private String name;
        private String email;
        private String password;
        private String phoneNumber = "";
        private List<JobDTO> jobs = new ArrayList<>();
        private List<OrderDTO> orders = new ArrayList<>();
        private Set<String> authorities = new HashSet<>();

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

        public Builder password(String password){
            this.password = password;
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

        public Builder authorities(Set<String> authorities){
            this.authorities = authorities;
            return this;
        }

        public UserDTO build(){

            if(this.name == null || this.email == null){
                throw new IllegalStateException("Um usuario deve possuir um nome, email e id");
            }

            return new UserDTO(id,name,email,password,phoneNumber,jobs,orders, authorities);

        }


    }

}
