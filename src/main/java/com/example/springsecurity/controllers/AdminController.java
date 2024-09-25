package com.example.springsecurity.controllers;


import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.payload.UserForm;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private final UserService userService;

    // XEM
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAllUsers() { return ResponseEntity.ok(userService.getAll());
    }
    // XÓA
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {;return ResponseEntity.ok(userService.delete(id));
    }
    // SỬA
    @PutMapping("/update-user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserForm form) { return ResponseEntity.ok(userService.update(id, form));
    }
    // XEM CHI TẾT
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {UserDto user = userService.getById(id);
        return ResponseEntity.ok(user);
    }
}
//
//    // SEARCH
//    @GetMapping("/search")
//    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query) {List<UserDto> users = userService.searchUser(query);
//        return ResponseEntity.ok(users);
//    }

