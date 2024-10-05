package com.example.springsecurity.model.payload;


public class ResponseError<T> extends ResponseData<T> {
    public ResponseError(int status, String message) {
        super(status, message);
    }
}
