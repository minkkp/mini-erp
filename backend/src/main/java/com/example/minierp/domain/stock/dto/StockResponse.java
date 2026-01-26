package com.example.minierp.domain.stock.dto;

import com.example.minierp.domain.item.ItemType;
import com.example.minierp.domain.stock.Stock;

public class StockResponse {
    private Long id;
    private Long itemId;
    private String itemName;
    private ItemType itemType;
    private String warehouseName;
    private int quantity;

    public StockResponse(Stock stock) {
        this.id = stock.getId();
        this.itemId = stock.getItem().getId();
        this.itemName = stock.getItem().getItemName();
        this.itemType = stock.getItem().getItemType();
        this.warehouseName = stock.getWarehouse().getWarehouseName();
        this.quantity = stock.getQuantity();
    }

    public Long getId() { return id; }

    public Long getItemId() { return itemId; }

    public String getItemName() {
        return itemName;
    }

    public ItemType getItemType() { return itemType; }

    public String getWarehouseName() {
        return warehouseName;
    }

    public int getQuantity() {
        return quantity;
    }
}
