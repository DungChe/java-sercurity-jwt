package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.MaintenanceDto;
import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.payload.request.ChangeStatusOrderForm;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.request.QuotationForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.MaintenaceService;
import com.example.springsecurity.service.OrderService;
import com.example.springsecurity.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consulting-staff")
public class ConsultingStaffController {

    @Autowired
    private QuotationService quotationService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MaintenaceService maintenaceService;

    // QUẢN LÍ ĐƠN HÀNG
    @GetMapping("/orders")
    public ResponseEntity<ResponseData<List<OrderDto>>> getAllOrders() {
        ResponseData<List<OrderDto>> response = orderService.getAll();
        return ResponseEntity.ok(response);
    }

    // XEM CHI TIẾT ĐƠN HÀNG
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ResponseData<OrderDto>> getOrderDetailsForAdmin(@PathVariable Long orderId) {
        ResponseData<OrderDto> order = orderService.getOrderDetailsForAdmin(orderId);
        return ResponseEntity.ok(order);
    }

    // THAY ĐỔI TRẠNG THÁI ĐƠN HÀNG
    @PatchMapping("/orders/change-status/{orderId}")
    public ResponseEntity<ResponseData<String>> changeStatusOrder(@PathVariable Long orderId, @RequestBody ChangeStatusOrderForm form) {
        ResponseData<String> response = orderService.changeStatusOrder(orderId, form);
        return ResponseEntity.ok(response);
    }

    // LẬP BẢNG BÁO GIÁ ( QUOTATIONS )
    @PostMapping("/create-quotation/{orderId}")
    public ResponseEntity<ResponseData<QuotationDto>> newQuo(@PathVariable Long orderId, @RequestBody QuotationForm form) {
        return ResponseEntity.ok(quotationService.newQuo(form, orderId));
    }

    // LẬP HÓA ĐƠN BẢO TRÌ ( MAINTENACE )
    @PostMapping("/create-maintenace/{orderId}")
    public ResponseEntity<ResponseData<MaintenanceDto>> createMaintenance( @RequestBody MaintenanceForm form, @PathVariable Long orderId){
        return ResponseEntity.ok(maintenaceService.createMaintenance(form, orderId));}


}
