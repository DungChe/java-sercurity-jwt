package com.example.springsecurity.config;

import com.example.springsecurity.model.entity.DesignRecord;
import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.Role;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.repository.DesignRecordRepository;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.RatingRepository;
import com.example.springsecurity.repository.RoleRepository;
import com.example.springsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      RoleRepository roleRepository,
                                      PasswordEncoder passwordEncoder,
                                      OrderRepository orderRepository,
                                      DesignRecordRepository designRepository,
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

            User user1 = createUser(userRepository, passwordEncoder, userRole, "user1", "phuclmdev@gmail.com" );
            User user2 = createUser(userRepository, passwordEncoder, userRole, "User2", "phuclmdev2@gmail.com" );
            User admin = createUser(userRepository, passwordEncoder, adminRole, "admin", "admin@gmail.com");
            User consultingStaff = createUser(userRepository, passwordEncoder, consultingStaffRole, "consultingStaff", "consulting@gmail.com");
            User designStaff = createUser(userRepository, passwordEncoder, designStaffRole, "designStaff", "design@gmail.com");
            User constructionStaff = createUser(userRepository, passwordEncoder, constructionStaffRole, "constructionStaff", "construction@gmail.com");
            User manager = createUser(userRepository, passwordEncoder, managerRole, "manager", "manager@gmail.com");

            String orderNumber1 = UUID.randomUUID().toString(); // Tạo số đơn hàng ngẫu nhiên
            Order order1 = new Order("Thông tin đơn hàng", null, orderNumber1, "09641623664","Cleaning", user1, "Quận 12", Order.ServiceType.DESIGN, LocalDate.now(), LocalDate.now().plusDays(7), Order.Status.INPROGRESS);
            String orderNumber2 = UUID.randomUUID().toString(); // Tạo số đơn hàng ngẫu nhiên
            Order order2 = new Order("Thông tin đơn hàng", null, orderNumber2,"0937473732", "Maintenance", user2, "Quận 12", Order.ServiceType.MAINTENANCE, LocalDate.now(), LocalDate.now().plusDays(14), Order.Status.COMPLETED);

            orderRepository.save(order1);
            orderRepository.save(order2);

            // Initialize designs
            DesignRecord design1 = new DesignRecord(null, designStaff, user1,"C:\\Users\\admin\\Downloads\\ban_thiet_ke",null, LocalDate.now(), LocalDate.now(), "Engineer notes here");
            designRepository.save(design1);

            // Initialize ratings
            Rating rating1 = new Rating(null, order1, user1, 5, "Excellent service!");
            Rating rating2 = new Rating(null, order2, user2, 4, "Very good!");
            ratingRepository.save(rating1);
            ratingRepository.save(rating2);
        };
    }

    private void insertRoles(RoleRepository roleRepository) {
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
        }
        if (roleRepository.findByName("ROLE_USER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_USER").build());
        }
        if (roleRepository.findByName("ROLE_CONSULTING_STAFF").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_CONSULTING_STAFF").build());
        }
        if (roleRepository.findByName("ROLE_DESIGN_STAFF").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_DESIGN_STAFF").build());
        }
        if (roleRepository.findByName("ROLE_CONSTRUCTION_STAFF").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_CONSTRUCTION_STAFF").build());
        }
        if (roleRepository.findByName("ROLE_MANAGER").isEmpty()) {
            roleRepository.save(Role.builder().name("ROLE_MANAGER").build());
        }
    }

    private User createUser(UserRepository userRepository, PasswordEncoder passwordEncoder, Role role, String username, String email) {
        User user = User.builder()
                .username(username)
                .email(email)
               .password(passwordEncoder.encode("123456"))
                .role(role)
                .createdDate(LocalDate.now())
                .status("ACTIVE")
                .build();
        return userRepository.save(user);
    }

}
