package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.payload.request.QuotationForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class QuoController {

    @Autowired
    private final QuotationService quotationService;

    @PostMapping("/new-quo")
    public ResponseEntity<ResponseData<QuotationDto>> createQuo( @RequestBody QuotationForm form){

        return ResponseEntity.ok(quotationService.newQuo(form));
    }
}
