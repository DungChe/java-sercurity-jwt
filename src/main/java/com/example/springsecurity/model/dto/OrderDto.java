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
    private String title;
    private Long orderId;
    private Long userId;
    private String userName;
    private String orderNumber;
    private String userPhone;
    private String designDetails;
    private String address;
    private Order.ServiceType serviceType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private String status;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .title(order.getTitle())
                .orderId(order.getOrderId())
                .userName(order.getUser().getUsername())
                .userId(order.getUser().getUserId())
                .orderNumber(order.getOrderNumber())
                .userPhone(order.getPhone())
                .designDetails(order.getDesignDetails())
                .serviceType(order.getServiceType())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .address(order.getAddress())
                .status(order.getStatus().toString())
                .build();
    }
}
