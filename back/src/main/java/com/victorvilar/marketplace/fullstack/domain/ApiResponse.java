package com.victorvilar.marketplace.fullstack.domain;

import java.time.LocalDate;
import java.util.List;

public class ApiResponse<T> {

    private LocalDate date;
    private T response;
    private boolean sucess;
    private String message;
    private List<String> errors;

    public ApiResponse(LocalDate date, T object,boolean sucess,String message, List<String> errors){
        this.date = date;
        this.response = object;
        this.sucess = sucess;
        this.message = message;
        this.errors = errors;
    }

    public LocalDate getDate(){
        return date;
    }

    public T getResponse(){
        return response;
    }

}
