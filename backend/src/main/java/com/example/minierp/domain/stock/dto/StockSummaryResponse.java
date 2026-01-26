package com.example.minierp.domain.stock.dto;


import com.example.minierp.domain.item.ItemType;

public class StockSummaryResponse {

    private Long itemId;
    private String itemName;
    private ItemType itemType;

    private int quantity;      // 현재 재고 (Stock)
    private int inboundQty;    // 입고 합계 (StockHistory)
    private int outboundQty;   // 출고 합계 (StockHistory)

    public StockSummaryResponse(
            Long itemId,
            String itemName,
            ItemType itemType,
            int quantity,
            int inboundQty,
            int outboundQty
    ) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.inboundQty = inboundQty;
        this.outboundQty = outboundQty;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getInboundQty() {
        return inboundQty;
    }

    public int getOutboundQty() {
        return outboundQty;
    }
}
