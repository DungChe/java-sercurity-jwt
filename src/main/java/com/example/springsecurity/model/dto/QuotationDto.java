package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.Quotation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class QuotationDto {
    private Long quotationId;
    private String quotationNumber;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String address;
    private String serviceType;
    private double areaSize; // Diện tích
    private String location; //
    private String designDetails;
    private double materialCost;       // Chi phí vật liệu dưới dạng double
    private double laborCost;           // Chi phí nhân công dưới dạng double
    private double transportationCost;   // Chi phí vận chuyển dưới dạng double
    private double totalCost;           // Tổng chi phí báo giá dưới dạng double
    private Quotation.PaymentMethod paymentMethod;
    private LocalDate quotationDate;
    private LocalDate expirationDate;
    private String status;

    public static QuotationDto toDto(Quotation quotation) {
        return QuotationDto.builder()
                .quotationId(quotation.getQuotationId())
                .quotationNumber(quotation.getQuotationNumber())
                .customerName(quotation.getCustomerName())
                .phoneNumber(quotation.getPhoneNumber())
                .email(quotation.getEmail())
                .address(quotation.getAddress())
                .serviceType(quotation.getServiceType())
                .areaSize(quotation.getAreaSize())  // Sử dụng diện tích dưới dạng double
                .location(quotation.getLocation()) // Thêm location
                .designDetails(quotation.getDesignDetails())
                .materialCost(quotation.getMaterialCost())  // Chi phí vật liệu
                .laborCost(quotation.getLaborCost())        // Chi phí nhân công
                .transportationCost(quotation.getTransportationCost()) // Chi phí vận chuyển
                .totalCost(quotation.getTotalCost())        // Tổng chi phí báo giá
                .paymentMethod(quotation.getPaymentMethod())
                .quotationDate(quotation.getQuotationDate())
                .expirationDate(quotation.getExpirationDate())
                .status(quotation.getStatus().toString())
                .build();
    }
}
