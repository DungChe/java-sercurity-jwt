package com.example.springsecurity.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class QuotationForm {

    @NotBlank(message = "Customer name is required")
//    private String customerName; // Tên khách hàng

//    @NotBlank(message = "Phone number is required")
//    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
//    private String phoneNumber; // Số điện thoại
//
//    @NotBlank(message = "Email is required")
//    @Email(message = "Email should be valid")
//    private String email; // Email
//
//    @NotBlank(message = "Address is required")
//    private String address; // Địa chỉ thi công
//
//    @NotBlank(message = "Service type is required")
//    private String serviceType; // Loại dịch vụ (Thiết kế, Thi công, Bảo dưỡng, v.v.)

    @NotNull(message = "Area size is required")
    private Double areaSize; // Diện tích thi công (m2)

    private String location; // Vị trí thi công

    @NotBlank(message = "Design details are required")
    private String designDetails; // Chi tiết thiết kế

    @NotNull(message = "Material cost is required")
    private Double materialCost; // Chi phí vật liệu

    @NotNull(message = "Labor cost is required")
    private Double laborCost; // Chi phí nhân công

    @NotNull(message = "Transportation cost is required")
    private Double transportationCost; // Chi phí vận chuyển

    @NotNull(message = "Total cost is required")
    private Double totalCost; // Tổng chi phí

    @NotNull(message = "Quotation date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate quotationDate; // Ngày lập báo giá

    @NotNull(message = "Expiration date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate; // Ngày hết hạn báo giá
}
