package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderDto {
    private Long orderId;
    private String designDetails;
    private String serviceType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private String status;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .designDetails(order.getDesignDetails())
                .serviceType(order.getServiceType().toString())   // toString do enum roi
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .status(order.getStatus().toString())           // toString do enum roi
                .build();
    }
}
