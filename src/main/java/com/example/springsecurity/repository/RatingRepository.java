package com.example.springsecurity.repository;


import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUser(User user);

}
