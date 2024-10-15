package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.UserBasic;
import com.example.springsecurity.model.payload.request.ChangePasswordForm;
import com.example.springsecurity.model.payload.request.UserForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    // Cập nhật thông tin cá nhân
    @PutMapping("/update/me")
    public ResponseEntity<ResponseData<String> > updateCurrentUser(@RequestBody UserForm form, Principal principal) {return ResponseEntity.ok(userService.updateMe(principal, form));
    }

    // Cập nhật mật khẩu
    @PatchMapping("/change-password")
    public ResponseEntity<ResponseData<String> > changePassword(@RequestBody ChangePasswordForm form, Principal principal) {return ResponseEntity.ok(userService.changePassword(form, principal));
    }

    // Lấy thông tin người dùng hiện tại
    @GetMapping("/me")
    public ResponseEntity<ResponseData<UserBasic> > getCurrentUser(Principal principal) {return ResponseEntity.ok(userService.getMe(principal));
    }

}
