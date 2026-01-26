package com.example.minierp.domain.item.dto;

import com.example.minierp.domain.item.ItemType;

public class ItemCreateRequest {
    private String itemName;
    private String unit;
    private Integer unitPrice;
    private ItemType itemType;

    public String getItemName() {
        return itemName;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
