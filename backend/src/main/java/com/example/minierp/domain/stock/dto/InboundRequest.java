package com.example.minierp.domain.stock.dto;

public class InboundRequest {
    private Long itemId;
    private Long warehouseId;
    private int quantity;

    public Long getItemId() {

        return itemId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public int getQuantity() {
        return quantity;
    }
}
