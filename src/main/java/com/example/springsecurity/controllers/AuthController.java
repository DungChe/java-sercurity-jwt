package com.example.springsecurity.controllers;

import com.example.springsecurity.model.payload.request.OTPForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.model.payload.request.SignInForm;
import com.example.springsecurity.model.payload.request.SignUpForm;
import com.example.springsecurity.service.AuthService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody SignInForm form){return ResponseEntity.ok(authService.login(form));
    }

    @PostMapping("/register")
    public ResponseEntity< ResponseData<String> > register(@RequestBody  SignUpForm form){return ResponseEntity.ok(authService.register(form));}

    @GetMapping("/refresh")

    public ResponseEntity refreshToken(@RequestHeader("X-Refresh-Token") String refreshToken){return ResponseEntity.ok(authService.refreshJWT(refreshToken));}


    @GetMapping("/confirm/{userId}")
    public ResponseData<String> confirm(@Min(1) @PathVariable Long userId, @RequestBody OTPForm otpForm) {
        log.info("Confirm user, userId={}, otpCode={}", userId, otpForm.getOtpCode());

        try {
            authService.confirmUser(userId, otpForm.getOtpCode());
            return new ResponseData<>(200, "User has confirmed successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(400, "Confirm was failed");
        }
    }

}