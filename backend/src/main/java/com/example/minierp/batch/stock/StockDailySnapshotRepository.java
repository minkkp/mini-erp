package com.example.minierp.batch.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StockDailySnapshotRepository
        extends JpaRepository<StockDailySnapshot, Long> {

    Optional<StockDailySnapshot>
    findByItemIdAndWarehouseIdAndSnapshotDate(
            Long itemId,
            Long warehouseId,
            LocalDate snapshotDate
    );
}