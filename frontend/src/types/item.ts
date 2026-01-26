export type ItemType = "RAW" | "FINISHED";

export interface ItemCreateRequest {
  itemCode: string;
  itemName: string;
  itemType: ItemType;
  unit?: string;
  unitPrice: number | null;
}

export interface ItemResponse {
  id: number;
  itemCode: string;
  itemName: string;
  itemType: ItemType;
  unit?: string;
  unitPrice: number | null;
}