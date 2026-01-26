package com.example.minierp.global;

import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ️도메인 예외
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException e) {
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "status", 400,
                        "message", e.getMessage()
                ));
    }

    // 동시성 예외 (@Version)
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLock() {
        return ResponseEntity
                .status(409)
                .body(Map.of(
                        "status", 409,
                        "message", "동시 처리로 재고가 변경되었습니다. 다시 시도하세요."
                ));
    }

}
