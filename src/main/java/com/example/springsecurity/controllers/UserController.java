package com.example.springsecurity.controllers;


import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.payload.ChangePasswordForm;
import com.example.springsecurity.model.payload.UserForm;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    // Cập nhật thông tin cá nhân
    @PutMapping("/update/me")
    public ResponseEntity<String> updateCurrentUser(@RequestBody UserForm form, Principal principal) {String result = userService.updateMe(principal, form);
        return ResponseEntity.ok(result);
    }
    // cập nhật mk
    @PatchMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordForm form, Principal principal) {String newPassWord = userService.changePassword(form, principal);
        return ResponseEntity.ok(newPassWord);
    }
    // Lấy thông tin người dùng hiện tại
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) { UserDto user = userService.getMe(principal);
        return ResponseEntity.ok(user);
    }

}
