package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.payload.ChangePasswordForm;
import com.example.springsecurity.model.payload.UserForm;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    List<UserDto> searchUser(String query);
    UserDto getById(Long id);
    String changePassword(ChangePasswordForm request, Principal connectedUser);
    String update(Long id, UserForm form);
    String delete(Long id);
    UserDto getMe(Principal principal);
    String updateMe(Principal principal, UserForm form);
}
