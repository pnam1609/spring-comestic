package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}