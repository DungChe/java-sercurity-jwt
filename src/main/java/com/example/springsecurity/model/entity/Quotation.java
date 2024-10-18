package com.example.springsecurity.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Quotations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quotationId;

    @Column(nullable = false)
    private String quotationNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String customerName; // Tên của người đặt

    @Column(nullable = false)
    private String phoneNumber; // Số điện thoại

    @Column(nullable = false)
    private String email; // Email

    @Column(nullable = false)
    private String address; // Địa chỉ thi công

    @Column(nullable = false)
    private String serviceType; // Loại dịch vụ (ví dụ: Thiết kế, Thi công, Bảo dưỡng, v.v.)

    @Column(nullable = false)
    private double areaSize; // Diện tích thi công (m2)

    private String location; // Vị trí cụ thể thi công (nếu có)

    private String designDetails; // Chi tiết thiết kế

    private double materialCost; // Chi phí vật liệu

    private double laborCost; // Chi phí nhân công

    private double transportationCost; // Chi phí vận chuyển

    private double totalCost; // Tổng chi phí

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod; // Phương thức thanh toán

    private LocalDate quotationDate; // Ngày lập báo giá

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING, APPROVED, REJECTED

    @Column(nullable = false)
    private LocalDate expirationDate; // Ngày hết hạn của báo giá

    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public enum PaymentMethod {
        CASH, CREDIT_CARD, BANK_TRANSFER, PAYPAL, MOMO // Các phương thức thanh toán
    }
}
