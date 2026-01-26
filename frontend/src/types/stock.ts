export interface StockResponse {
  id: number;
  itemId: number;
  itemName: string;
  itemType: "RAW" | "FINISHED";
  warehouseId: number;
  warehouseName: string;
  quantity: number;
}
export interface StockSummaryResponse {
  itemId: number;
  itemName: string;
  itemType: "RAW" | "FINISHED";
  quantity: number;
  inboundQty: number;
  outboundQty: number;
}
export type InboundHistoryResponse = {
  id : number;
  itemId: number;
  itemName: string;
  warehouseId: number;
  warehouseName: string;
  unit : string;
  unitPrice: number;
  changeQuantity: number;
  createdAt: string;
};