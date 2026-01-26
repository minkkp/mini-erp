package com.example.minierp.domain.item;

import com.example.minierp.domain.item.code.ItemCodeSequence;
import com.example.minierp.domain.item.code.ItemCodeSequenceRepository;
import com.example.minierp.domain.item.dto.ItemCreateRequest;
import com.example.minierp.domain.item.dto.ItemResponse;
import com.example.minierp.domain.production.ProductionMaterial;
import com.example.minierp.domain.production.ProductionMaterialRepository;
import com.example.minierp.domain.stock.StockRepository;
import com.example.minierp.global.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCodeSequenceRepository itemCodeSequenceRepository;
    private final StockRepository stockRepository;
    private final ProductionMaterialRepository productionMaterialRepository;

    public ItemService(ItemRepository itemRepository, ItemCodeSequenceRepository itemCodeSequenceRepository, StockRepository stockRepository, ProductionMaterialRepository productionMaterialRepository) {
        this.itemRepository = itemRepository;
        this.itemCodeSequenceRepository = itemCodeSequenceRepository;
        this.stockRepository = stockRepository;
        this.productionMaterialRepository = productionMaterialRepository;
    }

    public ItemResponse create(ItemCreateRequest request){

        ItemCodeSequence seq = itemCodeSequenceRepository.findById(request.getItemType()).orElseThrow();

        int no = seq.issue();

        String code = request.getItemType() == ItemType.RAW
                ? String.format("RAW-%04d", no)
                : String.format("FIN-%04d", no);

        Item item =
                request.getItemType() == ItemType.RAW
                        ? new Item(code, request.getItemName(),
                        ItemType.RAW,
                        request.getUnit(),
                        request.getUnitPrice())
                        : new Item(code, request.getItemName(),
                        ItemType.FINISHED);

        return new ItemResponse(itemRepository.save(item));
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> findAll(){
        return itemRepository.findAll().stream()
                .map(ItemResponse::new)
                .toList();
    }

    public void deactivate(Long id) {

        if (stockRepository.existsByItemIdAndQuantityGreaterThan(id, 0)) {
            throw new BusinessException("입고 처리된 항목은 삭제할 수 없습니다");

        }

        if (productionMaterialRepository.existsByItemId(id)) {
            throw new BusinessException("실적 처리에 사용된 자재는 삭제할 수 없습니다.");

        }

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.deactivate();
    }

}
