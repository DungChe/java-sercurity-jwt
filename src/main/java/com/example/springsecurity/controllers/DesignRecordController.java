package com.example.springsecurity.controllers;
import com.example.springsecurity.model.dto.DesignRecordDto;
import com.example.springsecurity.model.payload.request.FeedBackDesignRecordForm;
import com.example.springsecurity.model.payload.request.MaintenanceForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.DesignRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class DesignRecordController {
    private final DesignRecordService designRecordService;

    // Xem tất cả bản vẽ
    @GetMapping("/designs")
    public ResponseEntity<?> getAllDesignRecordsForCustomer() {
        return ResponseEntity.ok(designRecordService.getAllDesignRecordsForCustomer());
    }

    // Xem chi tiết bản vẽ
    @GetMapping("/designs/{recordId}")
    public ResponseEntity<?> getDesignRecordDetails(@PathVariable Long recordId) {
        return ResponseEntity.ok(designRecordService.getDesignRecordDetails(recordId));
    }

    @PostMapping("/designs/feedback/{recordId}")
    public ResponseEntity<ResponseData<DesignRecordDto>> updateCustomerFeedback(@PathVariable Long recordId, @RequestBody FeedBackDesignRecordForm form) {
        return ResponseEntity.ok(designRecordService.updateCustomerFeedback(recordId, form));
    }

}
