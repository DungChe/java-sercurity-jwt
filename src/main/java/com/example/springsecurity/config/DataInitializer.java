package com.example.springsecurity.config;

import com.example.springsecurity.model.entity.Design;
import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.repository.DesignRepository;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.RatingRepository;
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
    public CommandLineRunner initData(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder,
                                      OrderRepository orderRepository,
                                      DesignRepository designRepository,
                                      RatingRepository ratingRepository) {
        return args -> {
            // Insert roles
            insertRoles(roleRepository);

            // Create users
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
            Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
            Role consultingStaffRole = roleRepository.findByName("ROLE_CONSULTING_STAFF").orElse(null);
            Role designStaffRole = roleRepository.findByName("ROLE_DESIGN_STAFF").orElse(null);
            Role constructionStaffRole = roleRepository.findByName("ROLE_CONSTRUCTION_STAFF").orElse(null);
            Role managerRole = roleRepository.findByName("ROLE_MANAGER").orElse(null);

            User user1 = createUser(userRepository, passwordEncoder, userRole, "user1", "phuclmdev@gmail.com", "Customer User");
            User user2 = createUser(userRepository, passwordEncoder, userRole, "User2", "phuclmdev2@gmail.com", "Customer User");
            User admin = createUser(userRepository, passwordEncoder, adminRole, "admin", "admin@gmail.com", "Admin User");
            User consultingStaff = createUser(userRepository, passwordEncoder, consultingStaffRole, "consultingStaff", "consulting@gmail.com", "Consulting Staff User");
            User designStaff = createUser(userRepository, passwordEncoder, designStaffRole, "designStaff", "design@gmail.com", "Design Staff User");
            User constructionStaff = createUser(userRepository, passwordEncoder, constructionStaffRole, "constructionStaff", "construction@gmail.com", "Construction Staff User");
            User manager = createUser(userRepository, passwordEncoder, managerRole, "manager", "manager@gmail.com", "Manager User");

            // Initialize orders
            Order order1 = new Order(null, "Design 1 Details", user1, "Cleaning", LocalDate.now(), LocalDate.now().plusDays(7), "InProgress");
            Order order2 = new Order(null, "Design 2 Details", user2, "Maintenance", LocalDate.now(), LocalDate.now().plusDays(14), "Completed");
            orderRepository.save(order1);
            orderRepository.save(order2);

            // Initialize designs
            Design design1 = new Design(null, "Design for Koi Pond 1", "Approved", admin);
            Design design2 = new Design(null, "Design for Koi Pond 2", "Pending", admin);
            designRepository.save(design1);
            designRepository.save(design2);

            // Initialize ratings
            Rating rating1 = new Rating(null, order1, user1, 5, "Excellent service!");
            Rating rating2 = new Rating(null, order2, user2, 4, "Very good!");
            ratingRepository.save(rating1);
            ratingRepository.save(rating2);
        };
    }

    private void insertRoles(RoleRepository roleRepository) {
        Role adminRole = Role.builder().name("ROLE_ADMIN").build();
        Role userRole = Role.builder().name("ROLE_USER").build();
        Role consultingStaffRole = Role.builder().name("ROLE_CONSULTING_STAFF").build();
        Role designStaffRole = Role.builder().name("ROLE_DESIGN_STAFF").build();
        Role constructionStaffRole = Role.builder().name("ROLE_CONSTRUCTION_STAFF").build();
        Role managerRole = Role.builder().name("ROLE_MANAGER").build();

        // Save roles to the database
        roleRepository.save(adminRole);
        roleRepository.save(userRole);
        roleRepository.save(consultingStaffRole);
        roleRepository.save(designStaffRole);
        roleRepository.save(constructionStaffRole);
        roleRepository.save(managerRole);
    }

    private User createUser(UserRepository userRepository, PasswordEncoder passwordEncoder, Role role, String username, String email, String fullName) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode("123456"))
                .fullName(fullName)
                .role(role)
                .createdDate(LocalDate.now())
                .status("ACTIVE")
                .build();
        return userRepository.save(user);
    }
}
