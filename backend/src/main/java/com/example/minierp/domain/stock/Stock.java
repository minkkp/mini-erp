package com.example.minierp.domain.stock;

import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.warehouse.Warehouse;
import com.example.minierp.global.BusinessException;
import jakarta.persistence.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Entity
@Table(
        name="stock",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"item_id","warehouse_id"})
        }
)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Version
    private Long version;

    protected Stock(){}

    public Stock(Item item, Warehouse warehouse, int quantity) {
        this.item = item;
        this.warehouse = warehouse;
        this.quantity = quantity;
    }

    public void increase(int qty){
        this.quantity += qty;
    }

    public void decrease(int qty){
        if (this.quantity < qty) {
            throw new BusinessException("재고 부족");
        }
        this.quantity -= qty;
    }

    @PrePersist
    @PreUpdate
    protected  void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Item getItem() {
        return item;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public int getQuantity() {
        return quantity;
    }
}
