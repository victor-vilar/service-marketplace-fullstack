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

public class OrderDTO {

    private String id = null;
    private LocalDate creationDate;
    private String orderStatus = "";
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<Review> reviews = new ArrayList();
    private String observation = "";
    private PaymentDTO payment = null;
    private String customer;
    private String job;

    public OrderDTO(){

    }

    public OrderDTO(String id, LocalDate creationDate, String orderStatus, BigDecimal totalAmount, List<Review> reviews, String observation, PaymentDTO payment, String customer, String job){
       this.id = id;
       this.creationDate = creationDate;
       this.orderStatus = orderStatus;
       this.totalAmount = totalAmount;
       this.reviews = reviews;
       this.observation = observation;
       this.payment = payment;
       this.customer = customer;
       this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public static OrderDTO.Builder builder(){
        return new OrderDTO.Builder();
    }

    public static class Builder{
        private String id = null;
        private LocalDate creationDate;
        private String orderStatus = "";
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private List<Review> reviews = new ArrayList();
        private String observation = "";
        private PaymentDTO payment = null;
        private String customer;
        private String job;

        public Builder id(String id){
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

        public Builder payment(PaymentDTO payment){
            this.payment = payment;
            return this;
        }

        public Builder customer(String customer){
            this.customer = customer;
            return this;
        }

        public Builder job(String job){
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
