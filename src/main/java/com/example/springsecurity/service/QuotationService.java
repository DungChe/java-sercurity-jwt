package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.payload.request.QuotationForm;
import com.example.springsecurity.model.payload.response.ResponseData;

import java.util.List;

public interface QuotationService {
    ResponseData<QuotationDto> newQuo(QuotationForm quotationForm);

    ResponseData<List<QuotationDto>> getListQuoByUser();
    ResponseData<QuotationDto> approveQuotation(Long quoId);
    ResponseData<QuotationDto> rejectQuotation(Long quoId);
}
