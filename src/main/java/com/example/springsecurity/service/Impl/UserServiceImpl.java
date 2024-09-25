package com.example.springsecurity.service.Impl;

import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.ChangePasswordForm;
import com.example.springsecurity.model.payload.UserForm;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::from) // Sử dụng UserDto.from(user)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> searchUser(String query) {
        List<User> users = userRepository.searchUsersByFullNameOrEmail(query);
        return users.stream()
                .map(UserDto::from) // Sử dụng UserDto.from(user)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDto.from(user); // Sử dụng UserDto.from(user)
    }

    @Override
    public String changePassword(ChangePasswordForm request, Principal connectedUser) {
        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return "Current password is incorrect";
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return "New password and confirm password do not match";
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    @Override
    public String update(Long id, UserForm form) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(form.getFullName());
        user.setProfilePicture(form.getProfilePicture());

        userRepository.save(user);
        return "User updated successfully";
    }

    @Override
    public String delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public UserDto getMe(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return UserDto.from(user); // Sử dụng UserDto.from(user)
    }

    @Override
    public String updateMe(Principal principal, UserForm form) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(form.getFullName());
        user.setProfilePicture(form.getProfilePicture());

        userRepository.save(user);
        return "User updated successfully";
    }
}
