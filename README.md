# 생산 및 재고 관리를 위한 미니 ERP 프로젝트

ERP의 생산 및 재고 관리 기능에서 발생하는 동시 요청 문제와 트랜잭션 설계를  
Spring Boot + JPA 기반으로 해결하는 미니 프로젝트입니다.
<br><br>

## 🛠 기술 스택

### Backend
- Spring Boot
- Spring Data JPA
- PostgreSQL

### Frontend (백엔드 API 검증 목적)
- React
- TypeScript
<br><br>

## 🎯 프로젝트 목적

- 생산 처리 과정에서 발생할 수 있는 재고 불일치 문제를  
  도메인 규칙으로 해결
- 생산 처리 전 과정을 하나의 단위로 묶어 트랜잭션을 설계
- 동시 요청 상황에서 음수 재고 및 중복 차감이 발생하지 않도록
  데이터 무결성 유지
<br><br>

## 🧠 핵심 설계

### 동시 요청 처리

- Stock 엔티티에 낙관적 락(@Version) 적용
- 동시 요청 시 충돌을 감지하고 즉시 실패 처리
- 자동 재시도 대신 사용자 재시도 유도 전략 선택

### 생산 실적 처리

- 생산 실적 과정에서 발생하는 모든 과정을 하나의 트랜잭션으로 처리
- 처리 중 오류가 발생할 경우 전체 롤백
  
### 재고 음수 방지

- 재고 차감 시 Stock 엔티티 내부 로직에서 검증
- 잘못된 재고 정보가 DB에 저장되지 않도록 도메인 레벨에서 차단
<br><br>

## 🧩 도메인 구성

- Item  
  자재 및 제품 정보 (품목 코드, 단위 등)

- Stock  
  자재 및 제품의 재고 현황

- StockHistory  
  입고, 생산 시 재고 변경 이력 저장

- StockDailySnapshot  
  일 단위 재고 집계를 위한 스냅샷

- Production  
  생산 실적 정보

- ProductionMaterial  
  생산 시 투입된 자재 정보
<br><br>

## 🔄 생산 처리 흐름

1. 생산 제품 및 투입 자재 유효성 검증 (재고 체크)
2. 자재 재고 차감
3. 생산 실적 저장
4. 완제품 재고 증가
5. 재고 변경 이력 기록

모든 과정은 단일 트랜잭션으로 처리됩니다.
<br><br>

## 🚨 동시성 및 실패 전략

- 재고 동시 수정 충돌 발생 시
  - Optimistic Lock 예외 발생
  - HTTP 409 응답 반환
  - 사용자 재시도 유도

정합성이 중요한 도메인 특성상  
자동 재시도보다 명확한 실패를 우선하는 전략을 선택했습니다.
<br><br>

## 📊 재고 스냅샷 배치 처리

- 일 단위 재고 집계를 위한 스냅샷 테이블을 운영
- 배치 실행 시점의 전체 재고 상태를 기준으로 스냅샷 생성
- (item_id, warehouse_id, snapshot_date) 유니크 제약을 적용하여  
  동일 날짜에 여러 번 실행해도 결과가 동일하도록 설계

스냅샷 생성 배치는 PK 기반 페이징 방식으로 구현하여  
데이터 규모가 커져도 재고 데이터를 나눠 조회하며 생성할 수 있도록 구성했습니다.
<br><br>

## 🗂 ERD
<img width="979" height="716" alt="image" src="https://github.com/user-attachments/assets/51a9f153-8c66-4029-95f0-fd241577ef82" />

- 재고는 현재 상태와 변경 이력을 분리하여 관리
- 생산 실적과 자재 투입 정보 테이블을 분리
- 일 단위 재고 집계를 위해 StockDailySnapshot 테이블 구성
<br><br>

## 🧪 프론트엔드 구성 (보조)

API 및 시나리오 검증을 목적으로 구현했습니다.

- React + TypeScript 기반
- API 레이어 분리
- DTO 기준 타입 정의로 데이터 구조 일관성 유지
  
### 주요화면
<img width="1093" height="596" alt="image" src="https://github.com/user-attachments/assets/f23a5499-4b39-4144-a14c-3f29b07f93f3" />
<img width="1365" height="738" alt="image" src="https://github.com/user-attachments/assets/e5808621-795b-4b4f-925b-81a666ca0ee6" />
<img width="1238" height="384" alt="image" src="https://github.com/user-attachments/assets/fee359dd-5bfa-4950-9099-f42901545798" />
<br><br>

## ✍ 마무리

이 프로젝트를 통해  
생산 및 재고 관리 도메인에서 정합성과 트랜잭션 설계가  
왜 중요한지 직접 경험할 수 있었습니다.

단순 CRUD 구현을 넘어  
도메인 규칙을 중심으로 한 백엔드 설계를 목표로 한 프로젝트입니다.
