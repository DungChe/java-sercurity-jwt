package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Quotation;
import com.example.springsecurity.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    Quotation findByQuotationNumber(String quotationNumber);
    List<Quotation> findByUser(User user);


}
