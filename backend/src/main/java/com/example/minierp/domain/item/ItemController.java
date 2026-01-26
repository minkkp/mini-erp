package com.example.minierp.domain.item;

import com.example.minierp.domain.item.dto.ItemCreateRequest;
import com.example.minierp.domain.item.dto.ItemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item", description = "품목(자재/제품) 관리 API")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "품목/자재 등록")
    @PostMapping
    public ItemResponse create(
            @RequestBody ItemCreateRequest request
    ){
        return itemService.create(request);
    }

    @Operation(summary = "품목/자재 전체 조회")
    @GetMapping
    public List<ItemResponse> findAll(){
        return itemService.findAll();
    }

    @Operation(summary = "품목/자재 비활성화")
    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        itemService.deactivate(id);
    }

}
