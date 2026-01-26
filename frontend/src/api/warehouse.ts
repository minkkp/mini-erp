import { api } from "./client";
import type { WarehouseResponse } from "../types/warehouse";

export const fetchWarehouses = async (): Promise<WarehouseResponse[]> => {
  const { data } = await api.get<WarehouseResponse[]>("/warehouses");
  return data;
};