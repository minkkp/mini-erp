package com.example.minierp.domain.warehouse.dto;

import com.example.minierp.domain.warehouse.Warehouse;

public class WarehouseResponse {

    private Long id;
    private String warehouseName;
    private String location;

    public WarehouseResponse(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.warehouseName = warehouse.getWarehouseName();
        this.location = warehouse.getLocation();
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
