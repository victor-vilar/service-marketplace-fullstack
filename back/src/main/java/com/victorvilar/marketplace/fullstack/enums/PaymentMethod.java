package com.victorvilar.marketplace.fullstack.enums;

public enum PaymentMethod {

    PIX("Pix"),
    CARTAO_DEBITO("Cartão de Débito"),
    CARTAO_CREDITO("Cartão de Credito"),
    BOLETO("Boleto");

    PaymentMethod(String method){
        this.method = method;
    }

    private final String method;

    private String getMethod(){
        return method;
    }
}
