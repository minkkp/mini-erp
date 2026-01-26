package com.example.minierp.domain.history;

import com.example.minierp.domain.history.dto.StockHistoryResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryService(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<StockHistoryResponse> findAll() {
        return stockHistoryRepository.findAll().stream()
                .map(StockHistoryResponse::new)
                .toList();
    }

    public List<StockHistoryResponse> findInbound() {
        return stockHistoryRepository
                .findByChangeTypeOrderByCreatedAtDesc(StockChangeType.INBOUND)
                .stream()
                .map(StockHistoryResponse::new)
                .toList();
    }

    public List<StockHistoryResponse> findByItemId(Long itemId) {
        return stockHistoryRepository.findByItemId(itemId).stream()
                .map(StockHistoryResponse::new)
                .toList();
    }

    public List<StockHistoryResponse> findByWarehouseId(Long warehouseId) {
        return stockHistoryRepository.findByWarehouseId(warehouseId).stream()
                .map(StockHistoryResponse::new)
                .toList();
    }

    public List<StockHistoryResponse> findByRefId(Long refId) {
        return stockHistoryRepository.findByRefId(refId).stream()
                .map(StockHistoryResponse::new)
                .toList();
    }
}
