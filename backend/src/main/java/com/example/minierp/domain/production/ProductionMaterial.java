package com.example.minierp.domain.production;

import com.example.minierp.domain.item.Item;
import jakarta.persistence.*;

@Entity
@Table(name="production_material")
public class ProductionMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_id", nullable = false)
    private Production production;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected  ProductionMaterial(){}

    public ProductionMaterial(Production production, Item item, int quantity) {
        this.production = production;
        this.item = item;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Production getProduction() {
        return production;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }
}

