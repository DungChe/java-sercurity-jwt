package com.example.springsecurity.model.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpForm {
    private String fullName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String email;
    private String password;
    private String confirmPassword;


    public String getUsername() {
        return email;
    }
}