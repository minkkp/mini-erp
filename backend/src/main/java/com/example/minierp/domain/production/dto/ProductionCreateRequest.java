package com.example.minierp.domain.production.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductionCreateRequest {
    private Long productItemId;
    private Long warehouseId;
    private int goodQuantity;
    private int badQuantity;
    private int workMinutes;
    private LocalDateTime producedAt;
    private List<ProductionMaterialRequest> materials;

    public Long getProductItemId() { return productItemId; }

    public Long getWarehouseId() { return warehouseId; }

    public int getGoodQuantity() {
        return goodQuantity;
    }

    public int getBadQuantity() {
        return badQuantity;
    }

    public int getWorkMinutes() { return workMinutes; }

    public LocalDateTime getProducedAt() {
        return producedAt;
    }

    public List<ProductionMaterialRequest> getMaterials() {
        return materials;
    }
}
