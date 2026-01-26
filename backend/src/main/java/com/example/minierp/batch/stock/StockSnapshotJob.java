package com.example.minierp.batch.stock;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StockSnapshotJob {

    private final StockDailySnapshotService snapshotService;

    public StockSnapshotJob(StockDailySnapshotService snapshotService) {
        this.snapshotService = snapshotService;
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 00:00
    public void run() {
        snapshotService.createDailySnapshot(LocalDate.now());
    }
}