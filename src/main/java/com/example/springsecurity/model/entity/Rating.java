package com.example.springsecurity.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Order order; // Assuming service refers to an Order

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Refers to the User giving the rating

    private int rating;
    private String feedback;
}
