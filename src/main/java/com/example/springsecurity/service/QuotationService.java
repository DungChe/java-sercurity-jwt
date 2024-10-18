package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.QuotationDto;
import com.example.springsecurity.model.payload.request.QuotationForm;
import com.example.springsecurity.model.payload.response.ResponseData;

public interface QuotationService {
    ResponseData<QuotationDto> newQuo(QuotationForm quotationForm);
}
