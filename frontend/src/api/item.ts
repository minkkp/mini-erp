import { api } from "./client";
import type { ItemCreateRequest, ItemResponse } from "../types/item";

export const fetchItems = async () => {
  const { data } = await api.get<ItemResponse[]>("/items");
  return data;
};

export const createItem = async (data: ItemCreateRequest) => {
  const res = await api.post<ItemResponse>("/items", data);
  return res.data;
};

export const deleteItem = async (id: number) => {
  await api.delete(`/items/${id}`);
};