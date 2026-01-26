package com.example.minierp.domain.production;

import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.warehouse.Warehouse;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="production")
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_item_id",nullable = false)
    private Item productItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @OneToMany(mappedBy = "production", fetch = FetchType.LAZY)
    private List<ProductionMaterial> materials = new ArrayList<>();

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "good_quantity", nullable = false)
    private int goodQuantity;

    @Column(name = "bad_quantity", nullable = false)
    private int badQuantity;

    @Column(name = "work_minutes", nullable = false)
    private int workMinutes;

    @Column(name="produced_at",nullable = false)
    private LocalDateTime producedAt;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Production(){}

    public Production(Item productItem, Warehouse warehouse, int goodQuantity, int badQuantity, int workMinutes, LocalDateTime producedAt) {
        this.productItem = productItem;
        this.warehouse = warehouse;
        this.quantity = goodQuantity + badQuantity;
        this.goodQuantity = goodQuantity;
        this.badQuantity = badQuantity;
        this.workMinutes = workMinutes;
        this.producedAt = producedAt;
    }

    @PrePersist
    protected void onCreated(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Item getProductItem() {
        return productItem;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public List<ProductionMaterial> getMaterials() {
        return materials;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getGoodQuantity() {
        return goodQuantity;
    }

    public int getBadQuantity() {
        return badQuantity;
    }

    public int getWorkMinutes() {
        return workMinutes;
    }

    public LocalDateTime getProducedAt() {
        return producedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
