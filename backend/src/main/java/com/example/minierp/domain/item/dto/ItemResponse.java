package com.example.minierp.domain.item.dto;

import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.item.ItemType;

public class ItemResponse {
    private Long id;
    private String itemName;
    private String itemCode;
    private ItemType itemType;

    // RAW 전용
    private String unit;
    private Integer unitPrice;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemCode = item.getItemCode();
        this.itemType = item.getItemType();

        if (item.getItemType() == ItemType.RAW) {
            this.unit = item.getUnit();
            this.unitPrice = item.getUnitPrice();
        }
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitPrice() { return unitPrice; }
}
