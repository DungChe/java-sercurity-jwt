package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long id;
    private String token;
    private String refreshToken;
    private String status;
    private String result;

    public static AuthDto from(User user, String token, String refreshToken, String status, String result) {
        return AuthDto.builder()
                .id(user.getId())
                .token(token)
                .refreshToken(refreshToken)
                .status(status)
                .result(result)
                .build();
    }
}
