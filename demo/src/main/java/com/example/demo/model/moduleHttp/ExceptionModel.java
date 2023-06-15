package com.example.demo.model.moduleHttp;

public class ExceptionModel {
    private String exception;

    public ExceptionModel(Exception ex) {
        this.exception = ex.getMessage();
    }

    public ExceptionModel(String ex) {
        this.exception = ex;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
