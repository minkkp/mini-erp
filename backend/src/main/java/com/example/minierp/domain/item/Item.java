package com.example.minierp.domain.item;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Where(clause = "use_yn = true")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item_code", unique = true)
    private String itemCode;

    @Column(name="item_name", nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(name="item_type", nullable = false)
    private ItemType itemType;

    @Column(name="unit_price", nullable = true)
    private Integer unitPrice;

    @Column(name="unit", nullable = true)
    private String unit;

    @Column(name="use_yn", nullable = false)
    private boolean useYn = true;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    protected Item() {}

    // 제품 생성자
    public Item(String itemCode, String itemName, ItemType itemType) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    // 자재 생성자
    public Item(String itemCode,
                String itemName,
                ItemType itemType,
                String unit,
                Integer unitPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemType = itemType;
        this.unit = unit;
        this.unitPrice = unitPrice;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public boolean getUseYn() {
        return useYn;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void deactivate() {
        this.useYn = false;
    }
}