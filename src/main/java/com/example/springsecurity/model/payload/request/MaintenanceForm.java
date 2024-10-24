package com.example.springsecurity.model.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MaintenanceForm {
    private String serviceType;
    private String price;
    private String constructionStaff;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private String content;
}
