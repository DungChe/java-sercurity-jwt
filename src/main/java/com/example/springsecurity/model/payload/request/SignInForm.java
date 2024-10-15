package com.example.springsecurity.model.payload.request;

import lombok.Data;

@Data
public class SignInForm {
    private String email;
    private String password;
}