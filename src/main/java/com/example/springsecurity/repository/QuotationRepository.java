package com.example.springsecurity.repository;

import com.example.springsecurity.model.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    Quotation findByQuotationNumber(String quotationNumber);
}
