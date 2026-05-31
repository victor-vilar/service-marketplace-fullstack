package com.victorvilar.marketplace.fullstack.enums;

public enum OrderStatus {

    ABERTA("Aberta"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDO("Concluído");

    private final String status;

    OrderStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
