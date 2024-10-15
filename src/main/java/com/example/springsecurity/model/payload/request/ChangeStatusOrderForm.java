package com.example.springsecurity.model.payload.request;


import com.example.springsecurity.model.entity.Order;
import lombok.Data;

@Data
public class ChangeStatusOrderForm {
    private Order.Status status;
}
