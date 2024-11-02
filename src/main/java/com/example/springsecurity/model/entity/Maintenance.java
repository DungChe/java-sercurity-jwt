package com.example.springsecurity.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table( name = "maintence")
public class Maintenance {
    private String title;    // tieu de
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long maintenanceID;
    @OneToOne
    @JoinColumn( name = "order_id")
    private Order order;
    private String price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String constructionStaff;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate endDate;
    private String content;     // noi dung

}
