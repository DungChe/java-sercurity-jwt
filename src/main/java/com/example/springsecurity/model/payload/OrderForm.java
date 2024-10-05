package com.example.springsecurity.model.payload;

import com.example.springsecurity.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderForm {
    private String designDetails;
    private String serviceType; // e.g., "Cleaning", "Maintenance", etc.
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // e.g., "InProgress", "Completed"
}
