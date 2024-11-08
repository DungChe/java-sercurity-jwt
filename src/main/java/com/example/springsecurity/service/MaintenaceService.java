package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.MaintenanceDto;
import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.response.ResponseData;

import java.util.List;

public interface MaintenaceService {
    ResponseData<MaintenanceDto> createMaintenance(MaintenanceForm form, Long orderId);
    ResponseData<List<OrderDto>> getAll();
    // Chinh sua hon don bao tri
    ResponseData<MaintenanceDto> change(Long id, MaintenanceForm form);


}
