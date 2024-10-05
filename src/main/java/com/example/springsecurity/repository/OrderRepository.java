package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    boolean existsByUser(User user); // dùng khi dl user
}
