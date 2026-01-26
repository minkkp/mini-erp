package com.example.minierp.domain.production.dto;

import com.example.minierp.domain.production.ProductionMaterial;

public class ProductionMaterialResponse {
    private Long itemId;
    private String itemName;
    private int quantity;

    public ProductionMaterialResponse(ProductionMaterial pm) {
        this.itemId = pm.getItem().getId();
        this.itemName = pm.getItem().getItemName();
        this.quantity = pm.getQuantity();
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
