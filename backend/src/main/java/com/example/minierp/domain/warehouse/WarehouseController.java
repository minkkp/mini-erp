package com.example.minierp.domain.warehouse;

import com.example.minierp.domain.warehouse.dto.WarehouseCreateRequest;
import com.example.minierp.domain.warehouse.dto.WarehouseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Warehouse", description = "창고 관리 API")
@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @Operation(summary = "창고 등록")
    @PostMapping
    public WarehouseResponse create(
            @RequestBody WarehouseCreateRequest request
    ){
        return warehouseService.create(request);
    }

    @Operation(summary = "창고 전체조회")
    @GetMapping
    public List<WarehouseResponse> findAll(){
        return warehouseService.findAll();
    }

}
