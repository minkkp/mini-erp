package com.example.minierp.domain.production.dto;

import java.time.LocalDateTime;

public class ProductionMaterialRequest {
    public Long stockId;
    public int quantity;

    public Long getStockId() {
        return stockId;
    }

    public int getQuantity() {
        return quantity;
    }
}
