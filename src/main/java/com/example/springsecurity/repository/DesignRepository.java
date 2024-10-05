package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Design;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignRepository extends JpaRepository<Design, Long> {
    List<Design> findByStatus(String status);
    List<Design> findByDesigner(User designer);
    List<Design> findByDetailsContaining(String keyword);
    List<Design> findByStatusAndDesigner(String status, User designer);
    List<Design> findAllByOrderByStatusAsc();
}
