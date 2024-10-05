package com.example.springsecurity.service.Impl;

import com.example.springsecurity.exception.ResourceNotFoundException;
import com.example.springsecurity.model.dto.RatingDto;
import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.RatingRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Rating rateService(RatingDto ratingDto) {
        // Lấy thông tin khách hàng từ ID
        User customer = userRepository.findById(ratingDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        // Lấy thông tin dịch vụ từ ID đơn hàng
        Order order = orderRepository.findById(ratingDto.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        // Tạo mới đối tượng Rating từ DTO
        Rating rating = new Rating();
        rating.setUser(customer);
        rating.setService(order);
        rating.setRating(ratingDto.getRating());
        rating.setFeedback(ratingDto.getFeedback());

        // Lưu vào cơ sở dữ liệu
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getRatingsByCustomer(User user) {
        // Tìm kiếm danh sách đánh giá của khách hàng
        return ratingRepository.findByUser(user);
    }
}
