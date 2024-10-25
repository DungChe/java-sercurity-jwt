package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.Rating;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByUser(User user);

    Optional<Rating> findRatingByRatingId(Long ratingId);

    Optional<Rating> findByOrderAndUser(Order existingOrder, User currentUser);
}
