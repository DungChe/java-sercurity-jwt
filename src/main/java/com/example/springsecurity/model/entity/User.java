package com.example.springsecurity.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity
@Table(name ="Users")
public class User {
    @Id
    Long id;
    String name;


}
