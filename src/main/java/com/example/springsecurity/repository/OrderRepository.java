package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    boolean existsByUser(User user); // Kiểm tra sự tồn tại của order dựa trên user

//    boolean existsCanceled ("CANCELED");
    Optional<Order> findByOrderIdAndUserId(Long orderId, Long userId);

    Optional<Order> findById(Long orderId); // Truy vấn theo orderId
}
