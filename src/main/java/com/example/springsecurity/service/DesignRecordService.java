package com.example.springsecurity.service;

import com.example.springsecurity.model.dto.DesignRecordDto;
import com.example.springsecurity.model.payload.request.DesignRecordForm;
import com.example.springsecurity.model.payload.request.FeedBackDesignRecordForm;
import com.example.springsecurity.model.payload.response.ResponseData;

import java.util.List;

public interface DesignRecordService {
    ResponseData<DesignRecordDto> createDesignCord( DesignRecordForm form, Long customerId);
    ResponseData<DesignRecordDto> updateDesignRecord(Long recordId, DesignRecordForm form);
    ResponseData<List<DesignRecordDto>> getAllDesignRecordsForCustomer();
    ResponseData<DesignRecordDto> getDesignRecordDetails(Long recordId);
    ResponseData<DesignRecordDto> updateCustomerFeedback(Long recordId, FeedBackDesignRecordForm form);
    ResponseData<DesignRecordDto> deleteRecord(Long recordId);

}
