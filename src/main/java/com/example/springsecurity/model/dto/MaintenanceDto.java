package com.example.springsecurity.model.dto;

import com.example.springsecurity.model.entity.Maintenance;
import com.example.springsecurity.model.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class MaintenanceDto {
    private String title;
    private Long maintenanceID;
    private String numberOrder;
    private String serviceType;
    private String price;
    private Long userId;
    private String constructionStaff;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;

    public static MaintenanceDto toDto(Maintenance maintenance){
        return MaintenanceDto.builder()
                .title(maintenance.getTitle())
                .maintenanceID(maintenance.getMaintenanceID())
                .numberOrder(maintenance.getNumberOrder())
                .serviceType(maintenance.getServiceType())
                .price(maintenance.getPrice())
                .userId(maintenance.getUser().getId())
                .constructionStaff(maintenance.getConstructionStaff())
                .startDate(maintenance.getStartDate())
                .endDate(maintenance.getEndDate())
                .content(maintenance.getContent())
                .build();

    }


}
