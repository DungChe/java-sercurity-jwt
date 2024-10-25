package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public final class RatingResponseModel {
    private Long ratingId;
    private Long orderId;
    private String userEmail;
    private int rating;
    private String feedback;

    // Phương thức tĩnh để tạo RatingResponseModel
    public static RatingResponseModel toDto(Rating savedRating) {
        return RatingResponseModel.builder()
                .ratingId(savedRating.getRatingId())
                .orderId(savedRating.getOrder().getOrderId())
                .userEmail(savedRating.getUser().getEmail())
                .rating(savedRating.getRating())
                .feedback(savedRating.getFeedback())
                .build();
    }

}
