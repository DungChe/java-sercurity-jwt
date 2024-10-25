package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.DesignRecordDto;
import com.example.springsecurity.model.dto.MaintenanceDto;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.MaintenaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/consulting-staff") // Nhan vien tu van
public class MaintenaceController {
    @Autowired
    private MaintenaceService maintenaceService;

    // TAO HOA DON BAO TRI
    @PostMapping("/maintenace/create/{orderId}")
    public ResponseEntity<ResponseData<MaintenanceDto>> create(MaintenanceForm form, @PathVariable Long orderId){
        return ResponseEntity.ok(maintenaceService.createMaintenance(form, orderId));}

    // LAY DANH SACH HOA DON BAO TRi
    @GetMapping("/maintenace")
    public ResponseEntity<ResponseData<List<MaintenanceDto>>>  getAll(){
        return ResponseEntity.ok(maintenaceService.getAll());
    }

    // (them moi) Chinh sua hoa don bao tri
    @PutMapping("/maintenace/update/{maintenanceId}")
    public ResponseEntity<ResponseData<MaintenanceDto>> updateRecord(@PathVariable Long maintenanceId, @RequestBody MaintenanceForm form){
        return ResponseEntity.ok(maintenaceService.change(maintenanceId,form));
    }

}
