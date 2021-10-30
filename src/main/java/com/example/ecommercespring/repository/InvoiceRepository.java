package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}