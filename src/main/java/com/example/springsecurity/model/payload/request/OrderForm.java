package com.example.springsecurity.model.payload.request;

import com.example.springsecurity.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderForm {
    @NotBlank
    private String designDetails;
    @NotBlank
    private Order.ServiceType serviceType; // e.g., "Cleaning", "Maintenance", etc.

    private String userPhone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    @NotBlank
    private String address;
}
