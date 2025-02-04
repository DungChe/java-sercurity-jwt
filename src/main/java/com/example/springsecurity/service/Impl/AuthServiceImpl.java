package com.example.springsecurity.service.Impl;

import com.example.springsecurity.exception.EmailAlreadyExistsException;
import com.example.springsecurity.exception.InvalidRefreshTokenException;
import com.example.springsecurity.model.dto.AuthDto;
import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.request.SignInForm;
import com.example.springsecurity.model.payload.request.SignUpForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;

import com.example.springsecurity.security.JwtTokenProvider;
import com.example.springsecurity.service.AuthService;
import com.example.springsecurity.service.BlacklistTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
@Log4j2

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final BlacklistTokenService blacklistTokenService;

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

        log.info("User {} logged in successfully with ", user.getEmail());

        // Xác định trạng thái và thông điệp kết quả
        String status = "success";
        String result = "Login successful";

        return AuthDto.from(user, accessToken, refreshToken, status, result);
    }

    @Override
    public ResponseData<String> register(SignUpForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        if(role == null){
            return new ResponseError<>(404,"Not found role ROLE_USER");
        }

        // Tạo mã xác thực
        String otpCode = String.format("%06d", new Random().nextInt(999999));

        // Tạo đối tượng User
        User user = User.builder()
                .username(form.getUsername())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(role)
                .otpCode(otpCode)
                .status("INACTIVE") // Đặt trạng thái là "INACTIVE" cho đến khi xác thực email
                .build();

        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);
        log.info("User {} registered", user.getEmail());

//        String veryfyCode = UUID.randomUUID().toString();

        // Gửi thông điệp đến Kafka để gửi email xác nhận
        kafkaTemplate.send("confirm-account-topic", String.format("email=%s,id=%s,otpCode=%s", user.getEmail(), user.getId(), otpCode)); // Chỉnh sửa ở đây
        return new ResponseData<>(200,"Success register new user. Please check your email for confirmation", "Id: " + user.getUserId());
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

    @Override
    public String confirmUser(long userId, String otpCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the OTP matches
        if (!otpCode.equals(user.getOtpCode())) {
            log.error("OTP does not match for userId={}", userId);
            throw new IllegalArgumentException("OTP is incorrect");
        }

        user.setStatus("ACTIVE");
        user.setCreatedDate(LocalDate.now());
        userRepository.save(user);
        return "Confirmed!";
    }


    @Override
    public ResponseData<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Lấy JWT từ header Authorization
        String token = jwtTokenProvider.getJwtFromRequest(request);

        // Kiểm tra xem token có hợp lệ không
        if (token != null && jwtTokenProvider.validateAccessToken(token)) {
            // Lấy ngày hết hạn từ token
            Date expiryDateFromToken = jwtTokenProvider.getExpiryDateFromToken(token);

            // Chuyển đổi từ java.util.Date sang java.time.LocalDateTime
            LocalDateTime expiryDate = expiryDateFromToken.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // Lưu token vào danh sách đen
            blacklistTokenService.addTokenToBlacklist(token, expiryDate); // thêm token vào danh sách đen

            // Xóa cookie hoặc bất kỳ thông tin đăng nhập liên quan
            response.setHeader("Set-Cookie", "JSESSIONID=; HttpOnly; Path=/; Max-Age=0; Secure; SameSite=Strict");

            log.info("User logged out successfully with token: {}", token);
            return new ResponseData<>(200, "Đăng xuất thành công", null);
        }

        log.error("Logout failed: Invalid or expired token for request: {}", request.getRequestURI());
        return new ResponseError<>(400, "Token không hợp lệ hoặc đã hết hạn");
    }




}