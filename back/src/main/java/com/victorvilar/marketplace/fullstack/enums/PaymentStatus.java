package com.victorvilar.marketplace.fullstack.enums;

public enum PaymentStatus {


    ABERTA("Aberta"),
    CONCLUIDO("Concluído");

    private final String status;

    PaymentStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}
