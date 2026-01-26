package com.example.minierp.domain.production;

import com.example.minierp.domain.production.dto.ProductionCreateRequest;
import com.example.minierp.domain.production.dto.ProductionMaterialResponse;
import com.example.minierp.domain.production.dto.ProductionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Production", description = "생산 실적 관리 API")
@RestController
@RequestMapping("/productions")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(
            ProductionService productionService
    ) {
        this.productionService = productionService;
    }

    @Operation(
            summary = "생산 실적 등록",
            description = """
    생산 실적을 등록합니다.
    - 단일 트랜잭션으로 처리됩니다.
    - 자재 재고 차감 및 완제품 재고 증가가 함께 수행됩니다.
    - 재고 변경 시 낙관적 락(@Version)을 사용합니다.
    - 충돌 발생 시 트랜잭션은 전체 롤백됩니다.
    """
    )
    @PostMapping
    public Long create(@RequestBody ProductionCreateRequest request){
        return productionService.create(request);
    }

    @Operation(summary = "생산실적 전체조회")
    @GetMapping
    public List<ProductionResponse> findAll() {
        return productionService.findAll();
    }

    @Operation(summary = "생산 실적 ID별 조회")
    @GetMapping("/{id}")
    public ProductionResponse findById(@PathVariable Long id){
        return productionService.findById(id);
    }
}
