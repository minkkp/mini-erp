import { api } from "./client";
import type {
  ProductionCreateRequest,
  ProductionResponse,
} from "../types/production";

export const createProduction = async (
  data: ProductionCreateRequest
): Promise<ProductionResponse> => {
  const { data: res } = await api.post<ProductionResponse>(
    "/productions",
    data
  );
  return res;
};

export const fetchProductions = async (): Promise<ProductionResponse[]> => {
  const { data } = await api.get<ProductionResponse[]>("/productions");
  return data;
};

export const fetchProduction = async (
  id: number
): Promise<ProductionResponse> => {
  const { data } = await api.get<ProductionResponse>(
    `/productions/${id}`
  );
  return data;
};