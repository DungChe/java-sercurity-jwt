package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.MaintenanceDto;
import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.response.ResponseData;

import java.security.Principal;
import java.util.List;

public interface MaintenaceService {
    ResponseData<MaintenanceDto> createMaintenance(MaintenanceForm form, Long orderId);
    ResponseData<List<OrderDto>> getAllOrderMaintenance();
    // Chinh sua hon don bao tri
    ResponseData<MaintenanceDto> change(Long id, MaintenanceForm form);
    // User Xem hoa don bao tri cua minh
    ResponseData<List<MaintenanceDto>> userGetMaintenance(Principal principal);
    // Consulting staff xem chi tiet 1 hoa don bao tri
    ResponseData<MaintenanceDto> getMaintenanceById(Long id);
}
