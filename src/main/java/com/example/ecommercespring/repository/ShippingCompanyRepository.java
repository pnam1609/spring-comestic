package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.ShippingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingCompanyRepository extends JpaRepository<ShippingCompany, Long> {
}