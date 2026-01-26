package com.example.minierp.domain.stock;

import com.example.minierp.domain.history.StockChangeType;
import com.example.minierp.domain.history.StockHistory;
import com.example.minierp.domain.history.StockHistoryRepository;
import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.item.ItemRepository;
import com.example.minierp.domain.item.ItemType;
import com.example.minierp.domain.stock.dto.InboundRequest;
import com.example.minierp.domain.stock.dto.StockResponse;
import com.example.minierp.domain.stock.dto.StockSummaryResponse;
import com.example.minierp.domain.warehouse.Warehouse;
import com.example.minierp.domain.warehouse.WarehouseRepository;
import com.example.minierp.global.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final ItemRepository itemRepository;

    public StockService(StockRepository stockRepository, StockHistoryRepository stockHistoryRepository, WarehouseRepository warehouseRepository, ItemRepository itemRepository) {
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.warehouseRepository = warehouseRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public void inbound(InboundRequest request){

        if (request.getQuantity() <= 0) {
            throw new BusinessException("입고 수량은 0보다 커야 합니다.");
        }

        Item item = itemRepository
                .findById(request.getItemId())
                .orElseThrow();

        Warehouse warehouse = warehouseRepository
                .findById(request.getWarehouseId())
                .orElseThrow();

        Stock stock = stockRepository
                .findByItemAndWarehouse(item,warehouse)
                .orElseGet(() -> new Stock(item, warehouse, 0));

        stock.increase(request.getQuantity());
        stockRepository.save(stock);

        stockHistoryRepository.save(
                new StockHistory(
                        item.getId(),
                        item.getItemName(),
                        warehouse.getId(),
                        warehouse.getWarehouseName(),
                        item.getUnit(),
                        item.getUnitPrice(),
                        request.getQuantity(),
                        StockChangeType.INBOUND,
                        null
                )
        );
    }
    @Transactional
    public void deleteInboundHistory(Long historyId) {

        StockHistory history = stockHistoryRepository.findById(historyId)
                .orElseThrow(() -> new BusinessException("입고 내역이 존재하지 않습니다."));

        Item item = itemRepository.findById(history.getItemId())
                .orElseThrow(() -> new BusinessException("자재 정보를 찾을 수 없습니다."));

        Warehouse warehouse = warehouseRepository.findById(history.getWarehouseId())
                .orElseThrow(() -> new BusinessException("창고 정보를 찾을 수 없습니다."));

        Stock stock = stockRepository.findByItemAndWarehouse(item, warehouse)
                .orElseThrow(() -> new BusinessException("재고 정보가 존재하지 않습니다."));

        if (stock.getQuantity() < history.getChangeQuantity()) {
            throw new BusinessException("마이너스 재고는 발생할 수 없습니다");
        }

        stock.decrease(history.getChangeQuantity());

        stockHistoryRepository.delete(history);
    }

    public List<StockResponse> findAll(){
        return stockRepository.findAll().stream()
                .map(StockResponse::new)
                .toList();
    }

    public List<StockResponse> findByWarehouseId(Long warehouseId){
        Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow();
        return stockRepository.findByWarehouse(warehouse).stream()
                .map(StockResponse::new)
                .toList();
    }

    public List<StockResponse> findByItemType(ItemType itemType){
        return stockRepository.findByItem_ItemType(itemType).stream()
                .map(StockResponse::new)
                .toList();
    }

    public List<StockSummaryResponse> getStockSummary() {

        List<Item> items = itemRepository.findAll();

        return items.stream().map(item -> {

            int totalStockQty =
                    stockRepository.sumQuantityByItem(item);

            int inboundQty =
                    stockHistoryRepository.sumQuantityByItemAndType(
                            item.getId(), StockChangeType.INBOUND
                    );

            int outboundQty =
                    stockHistoryRepository.sumQuantityByItemAndType(
                            item.getId(), StockChangeType.PRODUCTION
                    );

            return new StockSummaryResponse(
                    item.getId(),
                    item.getItemName(),
                    item.getItemType(),
                    totalStockQty,
                    inboundQty,
                    outboundQty
            );
        })
        .filter(summary -> summary.getQuantity() > 0)
        .toList();
    }

}