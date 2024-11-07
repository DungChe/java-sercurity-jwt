package com.example.springsecurity.model.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DesignRecordForm {
    private MultipartFile drawingFile;
    private String engineerNotes;
}
