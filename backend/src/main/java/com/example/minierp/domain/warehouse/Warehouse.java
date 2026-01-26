package com.example.minierp.domain.warehouse;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="warehouse_name", nullable = false)
    private String warehouseName;

    @Column(name="location")
    private String location;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Warehouse(){}

    public Warehouse(String warehouseName, String location) {
        this.warehouseName = warehouseName;
        this.location = location;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getLocation() {
        return location;
    }
}
