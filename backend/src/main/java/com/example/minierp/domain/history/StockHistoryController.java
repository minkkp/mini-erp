package com.example.minierp.domain.history;

import com.example.minierp.domain.history.dto.StockHistoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "StockHistory", description = "재고 이력 관리")
@RestController
@RequestMapping("/stock-histories")
public class StockHistoryController {

    private final StockHistoryService stockHistoryService;

    public StockHistoryController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @Operation(summary = "재고 이력 전체 조회")
    @GetMapping
    public List<StockHistoryResponse> findAll() {
        return stockHistoryService.findAll();
    }

    @Operation(summary = "입고 이력 조회")
    @GetMapping("/inbound")
    public List<StockHistoryResponse> findInbound() {
        return stockHistoryService.findInbound();
    }

    @Operation(summary = "품목별 재고 이력 조회")
    @GetMapping("/item/{itemId}")
    public List<StockHistoryResponse> findByItem(@PathVariable Long itemId) {
        return stockHistoryService.findByItemId(itemId);
    }

    @Operation(summary = "창고별 재고 이력 조회")
    @GetMapping("/warehouse/{warehouseId}")
    public List<StockHistoryResponse> findByWarehouse(@PathVariable Long warehouseId) {
        return stockHistoryService.findByWarehouseId(warehouseId);
    }

    @Operation( summary = "참조 ID (실적ID) 기준 재고 이력 조회" )
    @GetMapping("/ref/{refId}")
    public List<StockHistoryResponse> findByRef(@PathVariable Long refId) {
        return stockHistoryService.findByRefId(refId);
    }
}
