package com.example.springsecurity.controllers;

import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.payload.request.QuotationForm;
import com.example.springsecurity.model.payload.response.ResponseData;
import com.example.springsecurity.service.QuotationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class QuoController {

    @Autowired
    private final QuotationService quotationService;

    @GetMapping("/quotations")
    public ResponseEntity<ResponseData<List<QuotationDto>>> getListQuoByUser (){
        return ResponseEntity.ok(quotationService.getListQuoByUser());
    }

    @PatchMapping("/quotations/my-quo/approve/{quoId}")
    public ResponseEntity<ResponseData<QuotationDto>> approveQuo(@PathVariable Long quoId){
        return ResponseEntity.ok(quotationService.approveQuotation(quoId));
    }

    @PatchMapping("/quotations/my-quo/reject/{quoId}")
    public ResponseEntity<ResponseData<QuotationDto>> rejectQuo( @PathVariable Long quoId){
        return ResponseEntity.ok(quotationService.rejectQuotation(quoId));
    }


}
