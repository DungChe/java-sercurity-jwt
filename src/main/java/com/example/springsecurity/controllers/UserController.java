package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.payload.ChangePasswordForm;
import com.example.springsecurity.model.payload.ResponseData;
import com.example.springsecurity.model.payload.ResponseError;
import com.example.springsecurity.model.payload.UserForm;
import com.example.springsecurity.service.UserService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated // Đảm bảo rằng các tham số có thể được kiểm tra tính hợp lệ
public class UserController {

    private final UserService userService; // Không cần @Autowired với @RequiredArgsConstructor

    // Cập nhật thông tin cá nhân
    @PutMapping("/update/me")
    public ResponseEntity<String> updateCurrentUser(@RequestBody UserForm form, Principal principal) {
        String result = userService.updateMe(principal, form);
        return ResponseEntity.ok(result);
    }

    // Cập nhật mật khẩu
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordForm form, Principal principal) {
        String newPassword = userService.changePassword(form, principal);
        return ResponseEntity.ok(newPassword);
    }

    // Lấy thông tin người dùng hiện tại
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        UserDto user = userService.getMe(principal);
        return ResponseEntity.ok(user);
    }

    // Phương thức xác thực người dùng
    @GetMapping("/confirm/{userId}")
    public ResponseData<String> confirm(@Min(1) @PathVariable int userId, @RequestParam String verifyCode) {
        log.info("Confirm user, userId={}, verifyCode={}", userId, verifyCode);

        try {
            userService.confirmUser(userId, verifyCode);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User has confirmed successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Confirm was failed");
        }
    }

}
