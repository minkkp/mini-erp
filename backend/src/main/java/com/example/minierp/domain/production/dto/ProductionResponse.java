package com.example.minierp.domain.production.dto;

import com.example.minierp.domain.production.Production;

import java.time.LocalDateTime;
import java.util.List;

public class ProductionResponse {
    private Long id;
    private Long productItemId;
    private String productItemName;
    private int goodQuantity;
    private int badQuantity;
    private int workMinutes;
    private LocalDateTime producedAt;
    private List<ProductionMaterialResponse> materials;

    public ProductionResponse(Production p) {
        this.id = p.getId();
        this.productItemId = p.getProductItem().getId();
        this.productItemName = p.getProductItem().getItemName();
        this.goodQuantity = p.getGoodQuantity();
        this.badQuantity = p.getBadQuantity();
        this.workMinutes = p.getWorkMinutes();
        this.producedAt = p.getProducedAt();
        this.materials = p.getMaterials()
                .stream()
                .map(ProductionMaterialResponse::new)
                .toList();
    }

    public Long getId() {
        return id;
    }

    public Long getProductItemId() {
        return productItemId;
    }

    public String getProductItemName() {
        return productItemName;
    }

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

    public List<ProductionMaterialResponse> getMaterials() {
        return materials;
    }
}
