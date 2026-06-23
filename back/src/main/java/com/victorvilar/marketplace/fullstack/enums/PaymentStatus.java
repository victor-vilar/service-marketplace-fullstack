package com.victorvilar.marketplace.fullstack.enums;

public enum PaymentStatus {


    EM_ABERTO("Em aberto"),
    PAGAMENTO_REALIZADO("Pagamento Realizado");

    private final String status;

    PaymentStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}
