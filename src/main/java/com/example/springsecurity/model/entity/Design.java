package com.example.springsecurity.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Designs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Design {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long designId;

    private String details;
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private User designer; // Refers to the User who designed the service
}
