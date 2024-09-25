package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String profilePicture;
    private String roleName;
    private LocalDate createdDate;
    private String status;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .roleName(user.getRole() != null ? user.getRole().getName() : null)
                .createdDate(user.getCreatedDate())
                .status(user.getStatus())
                .build();
    }
}
