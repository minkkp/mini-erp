package com.example.minierp.batch.stock;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "stock_daily_snapshot",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"item_id", "warehouse_id", "snapshot_date"}
                )
        }
)
public class StockDailySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long itemId;
    private Long warehouseId;
    private int quantity;
    private LocalDate snapshotDate;

    protected StockDailySnapshot() {}

    public StockDailySnapshot(Long itemId, Long warehouseId, int quantity, LocalDate snapshotDate) {
        this.itemId = itemId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.snapshotDate = snapshotDate;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }
}