package com.example.minierp.domain.stock;

import com.example.minierp.domain.item.ItemType;
import com.example.minierp.domain.stock.dto.InboundRequest;
import com.example.minierp.domain.stock.dto.StockResponse;
import com.example.minierp.domain.stock.dto.StockSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Stock", description = "재고 관리 API")
@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(
            summary = "자재 입고 처리",
            description = """
    자재 재고를 증가시킵니다.

    - 재고는 item + warehouse 기준으로 관리됩니다.
    - 재고 변경 후 이벤트를 발행하여 이력을 생성합니다.
    - 입고 수량이 0 이하인 경우 실패합니다.
    """
    )
    @PostMapping("/inbound")
    public void inbound(@RequestBody InboundRequest request){
        stockService.inbound(request);
    }

    @Operation(
            summary = "입고 내역 삭제",
            description = """
    자재 재고를 증가시킵니다.

    - 재고는 마이너스가 될 수 없습니다
    - 내역 삭제 시 자재 재고에 반영됩니다
    """
    )
    @DeleteMapping("/inbound-histories/{historyId}")
    public void deleteInboundHistory(@PathVariable Long historyId) {
        stockService.deleteInboundHistory(historyId);
    }

    @Operation(summary = "재고 전체조회")
    @GetMapping
    public List<StockResponse> findAll(){
       return stockService.findAll();
    }

    @Operation(summary = "창고별 재고조회")
    @GetMapping("/warehouse/{warehouseId}")
    public List<StockResponse> findByWarehouse(@PathVariable Long warehouseId){
        return stockService.findByWarehouseId(warehouseId);

    }

    @Operation(summary = "제품, 자재별 재고조회")
    @GetMapping("/type/{itemType}")
    public List<StockResponse> findByItemType(@PathVariable ItemType itemType){
        return stockService.findByItemType(itemType);
    }

    @Operation(summary = "자재별 입고/출고 집계용")
    @GetMapping("/summary")
    public List<StockSummaryResponse> summary() {
        return stockService.getStockSummary();
    }

}
