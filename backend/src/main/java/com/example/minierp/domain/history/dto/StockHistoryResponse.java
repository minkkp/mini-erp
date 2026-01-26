package com.example.minierp.domain.history.dto;

import com.example.minierp.domain.history.StockChangeType;
import com.example.minierp.domain.history.StockHistory;

import java.time.LocalDateTime;

public class StockHistoryResponse {
    private Long id;
    private Long itemId;
    private Long warehouseId;
    private String itemName;
    private String warehouseName;
    private int changeQuantity;
    private String unit;
    private Integer unitPrice;
    private StockChangeType changeType;
    private Long refId;
    private LocalDateTime createdAt;

    public StockHistoryResponse(StockHistory h) {
        this.id = h.getId();
        this.itemId = h.getItemId();
        this.itemName = h.getItemName();
        this.warehouseId = h.getWarehouseId();
        this.warehouseName = h.getWarehouseName();
        this.unit = h.getUnit();
        this.unitPrice = h.getUnitPrice();
        this.changeQuantity = h.getChangeQuantity();
        this.changeType = h.getChangeType();
        this.refId = h.getRefId();
        this.createdAt = h.getCreatedAt();
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getId() { return id; }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public int getChangeQuantity() {
        return changeQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public StockChangeType getChangeType() {
        return changeType;
    }

    public Long getRefId() {
        return refId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
