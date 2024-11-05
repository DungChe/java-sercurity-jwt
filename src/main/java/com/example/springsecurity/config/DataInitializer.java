//package com.example.springsecurity.config;
//
//import com.example.springsecurity.model.entity.Role;
//import com.example.springsecurity.model.entity.User;
//import com.example.springsecurity.repository.RoleRepository;
//import com.example.springsecurity.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDate;
//
//@Configuration
//public class DataInitializer {
//    @Bean
//    public CommandLineRunner initData(UserRepository userRepository,
//                                      RoleRepository roleRepository,
//                                      PasswordEncoder passwordEncoder) {
//        return args -> {
//            // Insert roles
//            insertRoles(roleRepository);
//
//            // Create users
//            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
//            Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
//            Role consultingStaffRole = roleRepository.findByName("ROLE_CONSULTING_STAFF").orElse(null);
//            Role designStaffRole = roleRepository.findByName("ROLE_DESIGN_STAFF").orElse(null);
//            Role constructionStaffRole = roleRepository.findByName("ROLE_CONSTRUCTION_STAFF").orElse(null);
//            Role managerRole = roleRepository.findByName("ROLE_MANAGER").orElse(null);
//
//            // Tạo người dùng
//            createUser(userRepository, passwordEncoder, userRole, "user1", "phuclmdev@gmail.com");
//            createUser(userRepository, passwordEncoder, userRole, "user2", "phuclmdev2@gmail.com");
//            createUser(userRepository, passwordEncoder, adminRole, "admin", "admin@gmail.com");
//            createUser(userRepository, passwordEncoder, consultingStaffRole, "consultingStaff", "consulting@gmail.com");
//            createUser(userRepository, passwordEncoder, designStaffRole, "designStaff", "design@gmail.com");
//            createUser(userRepository, passwordEncoder, constructionStaffRole, "constructionStaff", "construction@gmail.com");
//            createUser(userRepository, passwordEncoder, managerRole, "manager", "manager@gmail.com");
//        };
//    }
//
//    private void insertRoles(RoleRepository roleRepository) {
//        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
//            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
//        }
//        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
//            roleRepository.save(Role.builder().name("ROLE_USER").build());
//        }
//        if (roleRepository.findByName("ROLE_CONSULTING_STAFF").isEmpty()) {
//            roleRepository.save(Role.builder().name("ROLE_CONSULTING_STAFF").build());
//        }
//        if (roleRepository.findByName("ROLE_DESIGN_STAFF").isEmpty()) {
//            roleRepository.save(Role.builder().name("ROLE_DESIGN_STAFF").build());
//        }
//        if (roleRepository.findByName("ROLE_CONSTRUCTION_STAFF").isEmpty()) {
//            roleRepository.save(Role.builder().name("ROLE_CONSTRUCTION_STAFF").build());
//        }
//        if (roleRepository.findByName("ROLE_MANAGER").isEmpty()) {
//            roleRepository.save(Role.builder().name("ROLE_MANAGER").build());
//        }
//    }
//
//    private User createUser(UserRepository userRepository, PasswordEncoder passwordEncoder, Role role, String username, String email) {
//        User user = User.builder()
//                .username(username)
//                .email(email)
//                .password(passwordEncoder.encode("123456"))
//                .role(role)
//                .createdDate(LocalDate.now())
//                .status("ACTIVE")
//                .build();
//        return userRepository.save(user);
//    }
//}
