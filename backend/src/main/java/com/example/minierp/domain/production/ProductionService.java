package com.example.minierp.domain.production;

import com.example.minierp.domain.history.StockChangeType;
import com.example.minierp.domain.history.StockHistory;
import com.example.minierp.domain.history.StockHistoryRepository;
import com.example.minierp.domain.item.Item;
import com.example.minierp.domain.item.ItemRepository;
import com.example.minierp.domain.production.dto.ProductionCreateRequest;
import com.example.minierp.domain.production.dto.ProductionMaterialRequest;
import com.example.minierp.domain.production.dto.ProductionResponse;
import com.example.minierp.domain.stock.Stock;
import com.example.minierp.domain.stock.StockRepository;
import com.example.minierp.domain.warehouse.Warehouse;
import com.example.minierp.domain.warehouse.WarehouseRepository;
import com.example.minierp.global.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final ProductionMaterialRepository productionMaterialRepository;
    private final StockRepository stockRepository;
    private final StockHistoryRepository stockHistoryRepository;
    private final ItemRepository itemRepository;
    private final WarehouseRepository warehouseRepository;


    public ProductionService(ProductionRepository productionRepository, ProductionMaterialRepository productionMaterialRepository, StockRepository stockRepository, StockHistoryRepository stockHistoryRepository, ItemRepository itemRepository, WarehouseRepository warehouseRepository) {
        this.productionRepository = productionRepository;
        this.productionMaterialRepository = productionMaterialRepository;
        this.stockRepository = stockRepository;
        this.stockHistoryRepository = stockHistoryRepository;
        this.itemRepository = itemRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductionResponse> findAll() {
        return productionRepository.findAll()
                .stream()
                .map(ProductionResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductionResponse findById(Long id) {
        Production p = productionRepository.findById(id).orElseThrow();
        return new ProductionResponse(p);
    }

    /**
     * 생산 실적 등록 (모두 하나의 트랜잭션)
     * - 자재 재고 차감
     * - 완제품 재고 증가
     * - 재고 변경 이력 생성
     */
    @Transactional
    public Long create(ProductionCreateRequest request) {

        Item productItem = itemRepository.findById(request.getProductItemId())
                .orElseThrow(() -> new BusinessException("생산 품목이 존재하지 않습니다."));
        Warehouse warehouse =  warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new BusinessException("창고가 존재하지 않습니다."));

        Production production = createProduction(request, productItem, warehouse);

        consumeMaterials(production, request.getMaterials());

        increaseProductStock(production, productItem, warehouse, request);

        return production.getId();
    }

    private Production createProduction(
            ProductionCreateRequest request,
            Item productItem,
            Warehouse warehouse
    ) {
        Production production = new Production(
                productItem,
                warehouse,
                request.getGoodQuantity(),
                request.getBadQuantity(),
                request.getWorkMinutes(),
                request.getProducedAt()
        );
        return productionRepository.save(production);
    }

    private void consumeMaterials(
            Production production,
            List<ProductionMaterialRequest> materials
    ) {
        for (ProductionMaterialRequest m : materials) {

            Stock stock = stockRepository.findById(m.getStockId())
                    .orElseThrow(() -> new BusinessException("자재 재고 없음"));

            stock.decrease(m.getQuantity());

            saveStockHistory(
                    stock,
                    m.getQuantity(),
                    StockChangeType.PRODUCTION,
                    production.getId()
            );

            productionMaterialRepository.save(
                    new ProductionMaterial(production, stock.getItem(), m.getQuantity())
            );
        }
    }

    private void increaseProductStock(
            Production production,
            Item productItem,
            Warehouse warehouse,
            ProductionCreateRequest request
    ) {
        int producedQty = request.getGoodQuantity() - request.getBadQuantity();
        if (producedQty <= 0) {
            throw new BusinessException("생산수량이 없습니다");
        }

        Stock productStock = stockRepository
                .findByItemAndWarehouse(productItem, warehouse)
                .orElseGet(() -> new Stock(productItem, warehouse, 0));

        productStock.increase(producedQty);
        stockRepository.save(productStock);

        saveStockHistory(
                productStock,
                producedQty,
                StockChangeType.PRODUCTION,
                production.getId()
        );
    }

    private void saveStockHistory(
            Stock stock,
            int quantity,
            StockChangeType type,
            Long refId
    ) {
        stockHistoryRepository.save(
                new StockHistory(
                        stock.getItem().getId(),
                        stock.getItem().getItemName(),
                        stock.getWarehouse().getId(),
                        stock.getWarehouse().getWarehouseName(),
                        stock.getItem().getUnit(),
                        stock.getItem().getUnitPrice(),
                        quantity,
                        type,
                        refId
                )
        );
    }
}