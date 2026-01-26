export interface ProductionMaterialRequest {
  stockId: number;
  quantity: number;
}
export interface ProductionMaterialResponse {
  stockId: number;
  quantity: number;
}

export interface ProductionCreateRequest {
  productItemId: number;
  warehouseId : number;
  goodQuantity: number;
  badQuantity: number;
  producedAt: string;
  materials: ProductionMaterialRequest[];
}

export interface ProductionResponse {
  id: number;
  productItemId: number;
  productItemName: string;
  goodQuantity: number;
  badQuantity: number;
  workMinutes: number;
  producedAt: string;
  materials: ProductionMaterialResponse[];
}
