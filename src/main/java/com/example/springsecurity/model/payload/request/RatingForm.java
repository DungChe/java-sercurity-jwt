package com.example.springsecurity.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RatingForm {
    private int rating;
    private String feedback;
}
