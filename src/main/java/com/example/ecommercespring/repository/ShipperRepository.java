package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {
}