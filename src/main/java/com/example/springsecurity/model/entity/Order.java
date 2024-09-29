package com.example.springsecurity.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String designDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String serviceType; // "thiet ke", "bao tri", etc.
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // "peding , InProgress", "Completed, Canceled"

//    public enum ServiceType{
//        DESIGN,MAINTENANCE
//    }
//    public enum Status{
//        PENDING, INPROGRESS, COMPLETED, CANCELED
//    }
}
