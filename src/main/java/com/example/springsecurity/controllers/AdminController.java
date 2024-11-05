package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.dto.RatingResponseModel;
import com.example.springsecurity.model.dto.UserDto;
import com.example.springsecurity.model.payload.request.*;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.OrderService;
import com.example.springsecurity.service.QuotationService;
import com.example.springsecurity.service.RatingService;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manager")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private QuotationService quotationService;
    // XEM TẤT CẢ NGƯỜI DÙNG
    @GetMapping("/users")
    public ResponseEntity<ResponseData<List<UserDto>>> getAllUsers() {
        ResponseData<List<UserDto>> response = userService.getAll();
        return ResponseEntity.ok(response);
    }

    // XÓA NGƯỜI DÙNG
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<ResponseData<String>> deleteUser(@PathVariable Long id) {
        ResponseData<String> response = userService.delete(id);
        return ResponseEntity.ok(response);
    }

    // SỬA NGƯỜI DÙNG
    @PutMapping("/users/update/{id}")
    public ResponseEntity<ResponseData<String>> updateUser(@PathVariable Long id, @RequestBody UserForm form) {
        ResponseData<String> response = userService.update(id, form);
        return ResponseEntity.ok(response);
    }

    // XEM CHI TIẾT NGƯỜI DÙNG
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseData<UserDto>> getUserById(@PathVariable Long id) {
        ResponseData<UserDto> user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // CẤP QUYỀN
    @PatchMapping("/users/set-role/{id}")
    public ResponseEntity<ResponseData<Void>> setRoleUser(@PathVariable Long id, @RequestBody SetRoleForm form){
        return ResponseEntity.ok(userService.setRole(id, form));
    }

    // XEM TẤT CẢ ĐƠN HÀNG
    @GetMapping("/orders")
    public ResponseEntity<ResponseData<List<OrderDto>>> getAllOrders() {
        ResponseData<List<OrderDto>> response = orderService.getAll();
        return ResponseEntity.ok(response);
    }

    // CẬP NHẬT ĐƠN HÀNG
    @PutMapping("/orders/update/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> updateOrder(@PathVariable Long orderId, @RequestBody OrderForm form) {
        ResponseData<OrderDto> response = orderService.updateOrder(orderId, form);
        return ResponseEntity.ok(response);
    }

    // THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG
    @PatchMapping("/orders/change-status/{orderId}")
    public ResponseEntity<ResponseData<String>> changeStatusOrder(@PathVariable Long orderId, @RequestBody ChangeStatusOrderForm form) {
        ResponseData<String> response = orderService.changeStatusOrder(orderId, form);
        return ResponseEntity.ok(response);
    }

    // ADMIN XEM CHI TIẾT ĐƠN HÀNG
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> getOrderDetailsForAdmin(@PathVariable Long orderId) {
        ResponseData<OrderDto> order = orderService.getOrderDetailsForAdmin(orderId);
        return ResponseEntity.ok(order);
    }

    // Rating
    @GetMapping("/rating")
    public ResponseEntity<ResponseData<List<RatingResponseModel>>> getAll(){return ResponseEntity.ok(ratingService.getAll());}

    @DeleteMapping("/rating/delete/{ratingId}")
    public ResponseEntity<ResponseData<RatingResponseModel>>  delete(@PathVariable Long ratingId){return ResponseEntity.ok(ratingService.deleteRatingByAdmin(ratingId));}

    @PostMapping("/create-quotation/{oderId}")
    public ResponseEntity<ResponseData<QuotationDto>> createQuo(@RequestBody QuotationForm form, @PathVariable Long orderId){
        return ResponseEntity.ok(quotationService.newQuo(form, orderId));
    }
}
