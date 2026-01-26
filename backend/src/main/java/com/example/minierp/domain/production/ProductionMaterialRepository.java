package com.example.minierp.domain.production;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionMaterialRepository extends JpaRepository<ProductionMaterial, Long> {
    boolean existsByItemId(Long itemId);
}

