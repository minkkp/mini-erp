package com.example.minierp.domain.warehouse;

import com.example.minierp.domain.warehouse.dto.WarehouseCreateRequest;
import com.example.minierp.domain.warehouse.dto.WarehouseResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseResponse create(WarehouseCreateRequest request){
        Warehouse warehouse = new Warehouse(request.getWarehouseName(), request.getLocation());
        Warehouse saved = warehouseRepository.save(warehouse);
        return new WarehouseResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<WarehouseResponse> findAll(){
        return warehouseRepository.findAll().stream()
                .map(WarehouseResponse::new)
                .toList();
    }
}