package com.example.springsecurity.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String refreshToken) {
        super("Invalid refresh token: " + refreshToken);
    }

    public InvalidRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRefreshTokenException(Throwable cause) {
        super(cause);
    }
}
