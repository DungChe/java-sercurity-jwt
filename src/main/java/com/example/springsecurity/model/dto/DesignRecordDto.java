package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.DesignRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignRecordDto {
    private Long recordId;
    private String engineerName; // Têm Kỹ sư gửi bản vẽ
    private String customerName; // Tên khách hàng nhận bản vẽ
    private String drawingFile; // Đường dẫn đến file bản vẽ
    private String customerFeedback; // Phản hồi từ khách hàng
    private LocalDate creationDate; // Ngày lập hồ sơ
    private LocalDate updateDate; // Ngày cập nhật hồ sơ
    private String engineerNotes; // Ghi chú từ kỹ sư

    // Phương thức chuyển đổi từ DesignRecord sang DesignRecordDto
    public static DesignRecordDto toDto(DesignRecord designRecord) {
        if (designRecord == null) {
            return null; // Trả về null nếu designRecord là null
        }

        return DesignRecordDto.builder()
                .recordId(designRecord.getRecordId())
                .engineerName(designRecord.getEngineer().getUsername()) // Lấy ID kỹ sư
                .customerName(designRecord.getCustomer().getUsername()) // Lấy ID khách hàng
                .drawingFile(designRecord.getDrawingFile())
                .customerFeedback(designRecord.getCustomerFeedback())
                .creationDate(designRecord.getCreationDate())
                .updateDate(designRecord.getUpdateDate())
                .engineerNotes(designRecord.getEngineerNotes())
                .build();
    }
}
