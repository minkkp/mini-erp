package com.example.minierp.domain.history;

import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.warehouse.Warehouse;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="stock_history")
public class StockHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name="warehouse_id",nullable = false)
    private Long warehouseId;

    @Column(name = "warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "unit_price")
    private Integer unitPrice;

    @Column(name="change_quantity",nullable = false)
    private int changeQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name="change_type",nullable = false)
    private StockChangeType changeType;

    @Column(name="ref_id")
    private Long refId;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected  StockHistory(){}

    public StockHistory(Long itemId, String itemName, Long warehouseId, String warehouseName, String unit, Integer unitPrice, int changeQuantity, StockChangeType changeType, Long refId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.unit = unit;
        this.unitPrice = unitPrice;
        this.changeQuantity = changeQuantity;
        this.changeType = changeType;
        this.refId = refId;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Long getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getUnit() { return unit; }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public int getChangeQuantity() {
        return changeQuantity;
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
