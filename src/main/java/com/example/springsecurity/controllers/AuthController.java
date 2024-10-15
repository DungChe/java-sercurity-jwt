package com.example.springsecurity.controllers;

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
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
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
    public ResponseData<String> confirm(@Min(1) @PathVariable Long userId, @RequestParam String verifyCode) {
        log.info("Confirm user, userId={}, verifyCode={}", userId, verifyCode);

        try {
            authService.confirmUser(userId, verifyCode);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User has confirmed successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Confirm was failed");
        }
    }

}