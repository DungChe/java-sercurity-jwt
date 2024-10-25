package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaintenaceRepository extends JpaRepository<Maintenance, Long> {
    Optional<Maintenance> findById( Long maintenaceId);


}
