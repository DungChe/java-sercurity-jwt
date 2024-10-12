package com.example.springsecurity.service.Impl;

import com.example.springsecurity.exception.ResourceNotFoundException;
import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.entity.Order;
import com.example.springsecurity.model.entity.User;
import com.example.springsecurity.model.payload.request.ChangeStatusOrderForm;
import com.example.springsecurity.model.payload.request.OrderForm;
import com.example.springsecurity.repository.OrderRepository;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.OrderService;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public OrderDto placeOrder(OrderForm orderForm) {
        // Lấy thông tin xác thực từ SecurityContextHolder (người dùng hiện tại)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email người dùng

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // Tạo đối tượng Order mới
        Order order = new Order();
        order.setDesignDetails(orderForm.getDesignDetails());
        order.setServiceType(orderForm.getServiceType());
        order.setStartDate(orderForm.getStartDate());
        order.setEndDate(orderForm.getEndDate());
        order.setStatus("PENDING");

        // Gán người dùng hiện tại cho đơn hàng
        order.setUser(currentUser);

        // Lưu đơn hàng vào cơ sở dữ liệu
        Order savedOrder = orderRepository.save(order);

        // Chuyển đổi Order thành OrderDto và trả về
        return OrderDto.from(savedOrder);
    }

    // admin cập nhật đơn hàng
    @Override
    public OrderDto updateOrder(Long orderId, OrderForm orderForm) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        existingOrder.setDesignDetails(orderForm.getDesignDetails());
        existingOrder.setServiceType(orderForm.getServiceType());
        existingOrder.setStartDate(orderForm.getStartDate());
        existingOrder.setEndDate(orderForm.getEndDate());

        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderDto.from(updatedOrder);
    }
    @Override
    public String changeStatusOrder(Long orderID, ChangeStatusOrderForm form){
        Order existingOrder = orderRepository.findById(orderID)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        existingOrder.setStatus(form.getStatus());

        Order updateOrder = orderRepository.save(existingOrder);
        return "Change successfully";
    }

    @Override
    public String cancelOrder(Long orderId){
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email người dùng

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        // kiem tra co dom hang do ton tai hay khong
        Order existOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        // kiem tra don hang co phai cua user do khong
        Order orderAndUser = orderRepository.findByOrderIdAndUserId(existOrder.getOrderId(), currentUser.getUserId())
                        .orElseThrow(()-> new ResourceNotFoundException("User don't have order"));
        // kiem tra don hang khac trang thai da huy


        existOrder.setStatus("CANCELED");

        Order updateOrder = orderRepository.save(existOrder);
        return "Canceled successfully";
    }

    @Override
    public OrderDto updateOrderForUser(Long orderId, OrderForm orderForm) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        existingOrder.setDesignDetails(orderForm.getDesignDetails());
        existingOrder.setServiceType(orderForm.getServiceType());
        existingOrder.setStartDate(orderForm.getStartDate());
        existingOrder.setEndDate(orderForm.getEndDate());
        // Không cập nhật status

        Order updatedOrder = orderRepository.save(existingOrder);
        return OrderDto.from(updatedOrder);
    }

    // Quản li don hàng (admin)
    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }
    // Xem gio hàng của người dùng hiện tại
    @Override
    public List<OrderDto> getMyOrder() {
        // Lấy thông tin người dùng hiện tại từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email người dùng

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return orderRepository.findByUser(currentUser).stream()
                .map(OrderDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderDetailsForAdmin(Long orderId) {
        // Lấy đơn hàng dựa trên ID mà không cần kiểm tra người dùng
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return OrderDto.from(order);  // Chuyển đổi thành DTO để trả về
    }

    @Override
    public OrderDto getOrderDetailsForUser(Long orderId) {
        // Lấy đơn hàng dựa trên ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Kiểm tra xem đơn hàng có thuộc về người dùng hiện tại không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // Lấy email của người dùng đang đăng nhập

        // Kiểm tra xem đơn hàng này có thuộc về người dùng hiện tại không
        if (!order.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("You do not have access to this order");
        }

        return OrderDto.from(order);  // Chuyển đổi thành DTO để trả về
    }


}
