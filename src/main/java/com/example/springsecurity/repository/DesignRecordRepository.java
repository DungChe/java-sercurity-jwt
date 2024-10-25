package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.DesignRecord;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignRecordRepository extends JpaRepository<DesignRecord, Long> {
    Optional<DesignRecord> findById(Long recordId);
    List<DesignRecord> findAllByCustomer(User customer);
}
