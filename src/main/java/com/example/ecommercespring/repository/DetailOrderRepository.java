package com.example.ecommercespring.repository;

import com.example.ecommercespring.entity.DetailOrder;
import com.example.ecommercespring.key.DetailOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, DetailOrderKey> {
}
