package com.example.springsecurity.config;

import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_USER").build()));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_ADMIN").build()));

            // Tạo người dùng 1
            User user1 = User.builder()
                    .username("user1")
                    .email("phuclmdev@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .fullName("User One")
                    .role(userRole)
                    .createdDate(LocalDate.now())
                    .status("Active")
                    .build();

            // Tạo người dùng 2
            User user2 = User.builder()
                    .username("user")
                    .email("test2@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .fullName("User 2 ")
                    .role(userRole)
                    .createdDate(LocalDate.now())
                    .status("Active")
                    .build();
            User user3 = User.builder()
                    .username("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("123456"))
                    .fullName("admin ")
                    .role(adminRole)
                    .createdDate(LocalDate.now())
                    .status("Active")
                    .build();

            // Lưu người dùng vào cơ sở dữ liệu
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        };
    }
}
