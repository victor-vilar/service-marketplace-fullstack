package com.victorvilar.marketplace.fullstack.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date = LocalDate.now();
    private T response;
    private boolean sucess;
    private String message;
    private static final String DEFAULT_SUCCESS = "Operação Realizada com Sucesso !";
    private static final String DEFAULT_FAIL = "Erro !";

    public ApiResponse(){

    }

    public ApiResponse(T object,boolean sucess,String message, List<String> errors){
        this.response = object;
        this.sucess = sucess;
        this.message = message;
    }

    public LocalDate getDate(){
        return date;
    }

    public T getResponse(){
        return response;
    }


    public static ResponseBuilder success(){
        return new ResponseBuilder(true).message(DEFAULT_SUCCESS);
    }

    public static ResponseBuilder success(String message){
        return new ResponseBuilder(true).message(message);
    }

    public static <T> ApiResponse<T> success(T response){
        return success().build(response);
    }

    public static ResponseBuilder fail(){
        return new ResponseBuilder(false).message(DEFAULT_FAIL);
    }



    public static class ResponseBuilder{

        private boolean success;
        private String message = "";
        private List<String> errors = new ArrayList<>();

        public ResponseBuilder(boolean status){
            success = status;
        }

        public ResponseBuilder message(String message){
            this.message =message;
            return this;
        }


        public <T> ApiResponse<T> build(T response){
            return new ApiResponse<>(response,this.success,this.message,this.errors);

        }





    }

}
