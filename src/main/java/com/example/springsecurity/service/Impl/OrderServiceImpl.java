package com.example.springsecurity.service.Impl;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.request.ChangeStatusOrderForm;
import com.example.springsecurity.model.payload.request.OrderForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.model.payload.response.ResponseError;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseData<OrderDto> createOrder(OrderForm orderForm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return new ResponseError<>(400, "User not found with email: " + email);
        }

        Order order = new Order();
        order.setTitle("Thông tin đơn hàng");
        order.setDesignDetails(orderForm.getDesignDetails());
        order.setOrderNumber(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setServiceType(orderForm.getServiceType());
        order.setStartDate(orderForm.getStartDate());
        order.setEndDate(orderForm.getEndDate());
        order.setAddress(orderForm.getAddress());
        order.setStatus(Order.Status.PENDING);

        order.setUser(currentUser);
        Order savedOrder = orderRepository.save(order);
        return new ResponseData<>(200, "Order created successfully", OrderDto.from(savedOrder));
    }

    @Override
    public ResponseData<OrderDto> updateOrder(Long orderId, OrderForm orderForm) {
        Order existingOrder = orderRepository.findById(orderId).orElse(null);
        if (existingOrder == null) {
            return new ResponseError<>(404, "Order not found");
        }

        existingOrder.setDesignDetails(orderForm.getDesignDetails());
        existingOrder.setServiceType(orderForm.getServiceType());
        existingOrder.setStartDate(orderForm.getStartDate());
        existingOrder.setEndDate(orderForm.getEndDate());

        Order updatedOrder = orderRepository.save(existingOrder);
        return new ResponseData<>(200, "Order updated successfully", OrderDto.from(updatedOrder));
    }

    @Override
    public ResponseData<String> changeStatusOrder(Long orderID, ChangeStatusOrderForm form) {
        Order existOrder = orderRepository.findById(orderID).orElse(null);
        if (existOrder == null) {
            return new ResponseError<>(404, "Order does not exist");
        }

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();

        if (!existOrder.getStatus().equals(Order.Status.CANCELED)) {
            existOrder.setStatus(form.getStatus());
            orderRepository.save(existOrder);
        }
        return new ResponseData<>(200, "Change status successfully");
    }

    @Override
    public ResponseData<String> cancelOrder(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return new ResponseError<>(400, "User not found with email: " + email);
        }

        Order existOrder = orderRepository.findById(orderId).orElse(null);
        if (existOrder == null) {
            return new ResponseError<>(404, "Order not found");
        }

        orderRepository.findByOrderIdAndUserId(existOrder.getOrderId(), currentUser.getUserId())
                .orElse(null);

        if (existOrder.getStatus().equals(Order.Status.CANCELED)) {
            return new ResponseError<>(400, "Order already canceled");
        }

        existOrder.setStatus(Order.Status.CANCELED);
        orderRepository.save(existOrder);
        return new ResponseData<>(200, "Canceled successfully");
    }

    @Override
    public ResponseData<OrderDto> updateOrderForUser(Long orderId, OrderForm orderForm) {
        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email).orElse(null);
        if (currentUser == null) {
            return new ResponseError<>(401, "User not found with email: " + email);
        }

        // Tìm đơn hàng đã tồn tại
        Order existingOrder = orderRepository.findById(orderId)
                .orElse(null);
        if (existingOrder == null) {
            return new ResponseError<>(404, "Order not found");
        }

        // Cập nhật thông tin đơn hàng
        existingOrder.setDesignDetails(orderForm.getDesignDetails());
        existingOrder.setServiceType(orderForm.getServiceType());
        existingOrder.setStartDate(orderForm.getStartDate());
        existingOrder.setEndDate(orderForm.getEndDate());
        existingOrder.setAddress(orderForm.getAddress());

        Order updatedOrder = orderRepository.save(existingOrder);
        return new ResponseData<>(200, "Order updated successfully", OrderDto.from(updatedOrder));
    }

    @Override
    public ResponseData<List<OrderDto>> getAll() {
        // Lấy tất cả đơn hàng
        List<OrderDto> orders = orderRepository.findAll().stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
        return new ResponseData<>(200, "Orders retrieved successfully", orders);
    }

    @Override
    public ResponseData<List<OrderDto>> getMyOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElse(null);
        if (currentUser == null) {
            return new ResponseError<>(401, "User not found with email: " + email);
        }

        List<OrderDto> orders = orderRepository.findByUser(currentUser).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
        return new ResponseData<>(200, "My orders retrieved successfully", orders);
    }

    @Override
    public ResponseData<OrderDto> getOrderDetailsForAdmin(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElse(null);
        if (order == null) {
            return new ResponseError<>(404, "Order not found with id: " + orderId);
        }

        return new ResponseData<>(200, "Order details retrieved successfully", OrderDto.from(order));
    }

    @Override
    public ResponseData<OrderDto> getOrderDetailsForUser(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElse(null);
        if (order == null) {
            return new ResponseError<>(404, "Order not found with id: " + orderId);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (!order.getUser().getEmail().equals(email)) {
            return new ResponseError<>(403, "You do not have access to this order");
        }

        return new ResponseData<>(200, "Order details retrieved successfully", OrderDto.from(order));
    }
}
