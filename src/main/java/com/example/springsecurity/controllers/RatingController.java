package com.example.springsecurity.controllers;

import com.example.springsecurity.exception.ResourceNotFoundException;
import com.example.springsecurity.model.dto.RatingDto;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.RatingService;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService; // Thêm UserService để fetch thông tin khách hàng

    @PostMapping("/rate")
    public ResponseEntity<Rating> rateService(@RequestBody RatingDto ratingDto) {
        return ResponseEntity.ok(ratingService.rateService(ratingDto));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Rating>> getRatingsByCustomer(@PathVariable Long customerId) {
        // Fetch customer từ UserService
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return ResponseEntity.ok(ratingService.getRatingsByCustomer(customer));
    }
}
