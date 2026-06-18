package com.victorvilar.marketplace.fullstack.exceptions;

public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException(String msg){
        super(msg);
    }
}
