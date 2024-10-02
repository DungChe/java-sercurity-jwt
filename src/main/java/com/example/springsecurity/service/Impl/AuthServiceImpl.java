package com.example.springsecurity.service.Impl;

import com.example.springsecurity.exception.InvalidRefreshTokenException;
import com.example.springsecurity.model.dto.AuthDto;
import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.SignInForm;
import com.example.springsecurity.model.payload.SignUpForm;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;

import com.example.springsecurity.security.JwtTokenProvider;
import com.example.springsecurity.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;



    @Override
    public AuthDto login(SignInForm form) {
        // Kiểm tra xem người dùng có tồn tại không
        User user = userRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + form.getEmail()));

        if (!user.getStatus().equals("ACTIVE")) {
            throw new IllegalArgumentException("Account is not active");
        }
        // Thực hiện xác thực
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword())
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed for email: {} with exception: {}", form.getEmail(), e.getMessage());
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Nếu xác thực thành công
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        log.info("User {} logged in successfully", user.getEmail());

        // Xác định trạng thái và thông điệp kết quả
        String status = "success";
        String result = "Login successful";

        return AuthDto.from(user, accessToken, refreshToken, status, result);
    }



    @Override
    public String register(SignUpForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("Not found role ROLE_USER"));

        // Tạo đối tượng User mà không cần chỉ định status
        User user = User.builder()
                .fullName(form.getFullName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(role)
                .build(); // Không cần thêm .status("ACTIVE")

        userRepository.save(user);
        log.info("User {} registered", user.getUsername());
        return "Success register new user";
    }



    @Override
    public AuthDto refreshJWT(String refreshToken) {
        if (refreshToken != null) {
            refreshToken = refreshToken.replaceFirst("Bearer ", "");
            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                Authentication auth = jwtTokenProvider.createAuthentication(refreshToken);

                User user = userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + auth.getName()));

                log.info("User {} refreshed token", user.getUsername());

                // Xác định trạng thái và thông điệp kết quả
                String status = "success";
                String result = "Token refreshed successfully";

                return AuthDto.from(user, jwtTokenProvider.generateAccessToken(auth), refreshToken, status, result);
            }
        }
        throw new InvalidRefreshTokenException(refreshToken);
    }
}