package com.example.minierp.batch.stock;

import com.example.minierp.domain.stock.Stock;
import com.example.minierp.domain.stock.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


/**
 * 일별 재고 스냅샷 생성 배치
 * - 집계 목적의 보조 데이터
 * - 동일 날짜 재실행 시에도 동일 결과를 보장
 */
@Service
public class StockDailySnapshotService {


    private final StockRepository stockRepository;
    private final StockDailySnapshotRepository snapshotRepository;

    public StockDailySnapshotService(
            StockRepository stockRepository,
            StockDailySnapshotRepository snapshotRepository
    ) {
        this.stockRepository = stockRepository;
        this.snapshotRepository = snapshotRepository;
    }

    @Transactional
    public void createDailySnapshot(LocalDate date) {

        Long lastId = 0L;

        while (true) {

            List<Stock> stocks =
                    stockRepository.findTop1000ByIdGreaterThanOrderById(lastId);

            if (stocks.isEmpty()) {
                break;
            }

            for (Stock stock : stocks) {

                snapshotRepository
                        .findByItemIdAndWarehouseIdAndSnapshotDate(
                                stock.getItem().getId(),
                                stock.getWarehouse().getId(),
                                date
                        )
                        .ifPresentOrElse(
                                snapshot -> snapshot.updateQuantity(stock.getQuantity()),
                                () -> snapshotRepository.save(
                                        new StockDailySnapshot(
                                                stock.getItem().getId(),
                                                stock.getWarehouse().getId(),
                                                stock.getQuantity(),
                                                date
                                        )
                                )
                        );

                lastId = stock.getId();
            }
        }
    }
}