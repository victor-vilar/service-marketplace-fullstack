package com.victorvilar.marketplace.fullstack.domain;

import java.time.LocalDate;

public class ApiResponse<T> {

    private LocalDate date;
    private T response;

    public ApiResponse(LocalDate date, T object){
        this.date = date;
        this.response = object;
    }

    public LocalDate getDate(){
        return date;
    }

    public T getResponse(){
        return response;
    }

}
