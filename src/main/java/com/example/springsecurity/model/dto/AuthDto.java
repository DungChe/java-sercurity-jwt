package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {
    private Long id;
    private String token;
    private String refreshToken;
    private String status;
    private String result;

    public AuthDto(Long id, String token, String refreshToken, String status, String result) {
        this.id = id;
        this.token = token;
        this.refreshToken = refreshToken;
        this.status = status;
        this.result = result;
    }

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
