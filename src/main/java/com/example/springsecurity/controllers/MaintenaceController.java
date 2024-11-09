package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.DesignRecordDto;
import com.example.springsecurity.model.dto.MaintenanceDto;
import com.example.springsecurity.model.dto.OrderDto;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.Impl.OrderServiceImpl;
import com.example.springsecurity.service.MaintenaceService;
import com.example.springsecurity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users") // Nhan vien tu van
public class MaintenaceController {
    @Autowired
    private MaintenaceService maintenaceService;

    // Bao tri
    @GetMapping("/my-maintenance")
    public ResponseEntity<ResponseData<List<MaintenanceDto>>> userGetMyMaintenance(Principal principal) {
        return ResponseEntity.ok(maintenaceService.userGetMaintenance(principal));
    }


}
