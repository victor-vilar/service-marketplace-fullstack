package com.victorvilar.marketplace.fullstack.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiResponse<T> {

    private LocalDate date = LocalDate.now();
    private T response;
    private boolean sucess;
    private String message;
    private List<String> errors;
    private static final String DEFAULT_SUCCESS = "Operação Realizada com Sucesso !";
    private static final String DEFAULT_FAIL = "Erro !";

    public ApiResponse(){

    }

    public ApiResponse(T object,boolean sucess,String message, List<String> errors){
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


    public static ResponseBuilder success(){
        return new ResponseBuilder(true, DEFAULT_SUCCESS);
    }

    public static ResponseBuilder success(String message){
        return new ResponseBuilder(true,message);
    }

    public static ResponseBuilder fail(List<String> errors){
        return new ResponseBuilder(false,errors);
    }

    public static <T> ApiResponse<T> success(T response){
        return success().build(response);
    }

    public static class ResponseBuilder{

        private boolean success;
        private String message = "";
        private List<String> errors = new ArrayList<>();

        public ResponseBuilder(boolean success, String message){
            this.success = success;
            this.message = message;
        }

        public ResponseBuilder(boolean success,List<String> errors){
            this.success = success;
            this.errors = errors;
        }

        public <T> ApiResponse<T> build(T response){
            return new ApiResponse<>(response,this.success,this.message,this.errors);

        }





    }

}
