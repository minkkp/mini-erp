import { api } from "./client";
import type {
  InboundHistoryResponse,
  StockResponse,
  StockSummaryResponse,
} from "../types/stock";

export const inboundStock = async (data: {
  itemId: number;
  warehouseId: number;
  quantity: number;
}): Promise<void> => {
  await api.post("/stocks/inbound", data);
};

export const deleteInboundHistory = async (id: number): Promise<void> => {
  await api.delete(`/stocks/inbound-histories/${id}`);
};

export const fetchStocks = async (): Promise<StockResponse[]> => {
  const { data } = await api.get<StockResponse[]>("/stocks");
  return data;
};

export const fetchStockSummary = async (): Promise<StockSummaryResponse[]> => {
  const { data } = await api.get<StockSummaryResponse[]>("/stocks/summary");
  return data;
};

export const fetchInboundHistories = async (): Promise<
  InboundHistoryResponse[]
> => {
  const { data } = await api.get<InboundHistoryResponse[]>(
    "/stock-histories/inbound"
  );
  return data;
};
