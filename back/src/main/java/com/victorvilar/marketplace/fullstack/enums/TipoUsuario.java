package com.victorvilar.marketplace.fullstack.enums;

public enum TipoUsuario {

    USUARIO("Usuario"),
    ADMINISTRADOR("Administrador");


    private final String tipo;

    TipoUsuario(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

}
