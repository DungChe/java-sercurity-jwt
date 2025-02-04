package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBasic {
    private String username;
    private String email;
    private String phone;
    private LocalDate createdDate;
    private LocalDate dob;

    public static UserBasic to(User user) {
        return UserBasic.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .createdDate(user.getCreatedDate())
                .dob(user.getDob())
                .build();
    }

}
