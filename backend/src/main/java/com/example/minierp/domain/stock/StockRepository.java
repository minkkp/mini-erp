package com.example.minierp.domain.stock;

import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.item.ItemType;
import com.example.minierp.domain.warehouse.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByItemAndWarehouse(Item item, Warehouse warehouse);
    List<Stock> findByItem_ItemType(ItemType itemType);
    List<Stock> findByWarehouse(Warehouse warehouse);
    boolean existsByItemIdAndQuantityGreaterThan(Long itemId, int quantity);
    @Query("""
    select coalesce(sum(s.quantity), 0)
    from Stock s
    where s.item = :item
    """)
    int sumQuantityByItem(@Param("item") Item item);

    List<Stock> findTop1000ByIdGreaterThanOrderById(Long lastId);

}