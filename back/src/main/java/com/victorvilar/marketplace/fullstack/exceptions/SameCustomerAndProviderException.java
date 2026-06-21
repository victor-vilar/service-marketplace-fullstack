package com.victorvilar.marketplace.fullstack.exceptions;

public class SameCustomerAndProviderException extends RuntimeException{

    public SameCustomerAndProviderException(String msg){
        super(msg);
    }
}
