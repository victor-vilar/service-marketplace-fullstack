package com.victorvilar.marketplace.fullstack.enums;

public enum Reviewer {

    PROVEDOR("Provedor"),
    CLIENTE("Cliente");

    private final String tipo;

    Reviewer(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }
}
