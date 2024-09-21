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
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> searchUser(String query) {
        List<User> users = userRepository.searchUsersByFullNameOrEmail(query);
        return users.stream().
                map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return convertToDto(user);
    }

    @Override
    public String changePassword(ChangePasswordForm request, Principal connectedUser) {
        // Tìm người dùng dựa trên email của người dùng hiện tại
        User user = userRepository.findByEmail(connectedUser.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return "Current password is incorrect";
        }

        // Kiểm tra mật khẩu mới và xác nhận mật khẩu có khớp nhau không
        if (!request.getNewPassword().equals( request.getConfirmPassword()) ) {
            return "New password and confirm password do not match";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    @Override
    public String update(Long id, UserForm form) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(form.getFullName());
//        user.setProfilePicture(form.getProfilePicture());  // Cập nhật profilePicture
        // set other fields from form if needed

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
        return convertToDto(user);
    }

    @Override
    public String updateMe(Principal principal, UserForm form) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFullName(form.getFullName());
//        user.setProfilePicture(form.getProfilePicture());  // Cập nhật profilePicture
        // set other fields from form if needed

        userRepository.save(user);
        return "User updated successfully";
    }

    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
//                .profilePicture(user.getProfilePicture())  // Thêm profilePicture
                // set other fields if needed
                .build();
    }
}
