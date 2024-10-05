package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.RatingDto;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.User;

import java.util.List;

public interface RatingService {
    Rating rateService(RatingDto ratingDto);
    List<Rating> getRatingsByCustomer(User customer);
}
