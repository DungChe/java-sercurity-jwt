package com.example.springsecurity.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String title;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderNumber;
    private String designDetails;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String address;
    private ServiceType serviceType; // DESIGN,MAINTENANCE,REPAIR etc.
    private LocalDate startDate; 
    private LocalDate endDate;
    private Status status; // "peding , InProgress", "Completed, Canceled"

    public enum ServiceType{
        DESIGN,MAINTENANCE,REPAIR
    }
    public enum Status{
        PENDING, INPROGRESS, COMPLETED, CANCELED
    }
}
