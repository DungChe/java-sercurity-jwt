package com.example.springsecurity.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Long ratingId;
    private Long serviceId;
    private Long customerId;
    private int rating;
    private String feedback;
}
