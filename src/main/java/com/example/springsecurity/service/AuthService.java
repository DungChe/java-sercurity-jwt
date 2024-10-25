package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.AuthDto;
import com.example.springsecurity.model.payload.request.SignInForm;
import com.example.springsecurity.model.payload.request.SignUpForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;

public interface AuthService {
    AuthDto login(SignInForm form);
    ResponseData<String> register(SignUpForm form);
    AuthDto refreshJWT(String refreshToken);
    String confirmUser(long userId, String otpCode);

}