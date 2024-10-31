package com.example.springsecurity.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "DesignRecords")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  DesignRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    @ManyToOne
    @JoinColumn(name = "design_staff_id", nullable = false)
    private User engineer; // Kỹ sư gửi bản vẽ

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer; // Khách hàng nhận bản vẽ

    @Column(nullable = false)
    private String drawingFile; // Đường dẫn đến file bản vẽ (hình ảnh, PDF...)

    @Column(length = 1000)
    private String customerFeedback; // Phản hồi từ khách hàng

    @Column(nullable = false)
    private LocalDate creationDate; // Ngày lập hồ sơ

    private LocalDate updateDate; // Ngày cập nhật hồ sơ

    @Column(length = 1000)
    private String engineerNotes; // Ghi chú từ kỹ sư (có thể cập nhật khi nhận phản hồi từ khách hàng)

}
