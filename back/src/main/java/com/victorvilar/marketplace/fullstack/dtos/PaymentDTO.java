package com.victorvilar.marketplace.fullstack.dtos;

public class PaymentDTO{

    private String id;
    private String paymentStatus;
    private String paymentMethod;
    private String order;

    public PaymentDTO(){

    }

    public PaymentDTO(String id, String paymentStatus, String paymentMethod, String order){
        this.id = id;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
        this.order = order;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        String id = null;
        String status;
        String method;
        String order;

        public Builder id(String id){
            this.id = id;
            return this;
        }

        public Builder status(String paymentStatus){
            this.status = paymentStatus;
            return this;
        }

        public Builder method(String paymentMethod){
            this.method = paymentMethod;
            return this;
        }

        public Builder order (String order){
            this.order = order;
            return this;
        }

        public PaymentDTO build(){
            return new PaymentDTO(id, status,method,order);
        }
    }

}
