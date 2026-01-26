package com.example.minierp.domain.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByItemId(Long itemId);
    List<StockHistory> findByWarehouseId(Long warehouseId);
    List<StockHistory> findByRefId(Long refId);
    List<StockHistory> findByChangeTypeOrderByCreatedAtDesc(StockChangeType changeType);

    @Query("""
        select coalesce(sum(h.changeQuantity), 0)
        from StockHistory h
        where h.itemId =:itemId
          and h.changeType = :type
    """)
    int sumQuantityByItemAndType(
            @Param("itemId") Long itemId,
            @Param("type") StockChangeType type
    );
}
