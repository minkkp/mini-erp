import { useEffect, useState } from 'react';
import { fetchItems } from '../api/item';
import { inboundStock } from '../api/stock';
import { deleteInboundHistory } from '../api/stock';
import { fetchWarehouses } from '../api/warehouse';
import { fetchInboundHistories } from '../api/stock';
import type { ItemResponse } from '../types/item';
import type { WarehouseResponse } from '../types/warehouse';
import type { InboundHistoryResponse } from '../types/stock';

type InboundRow = {
  itemId: number | '';
  warehouseId: number | '';
  quantity: number;
};

export default function InboundPage() {
  const [items, setItems] = useState<ItemResponse[]>([]);
  const [warehouses, setWarehouses] = useState<WarehouseResponse[]>([]);
  const [histories, setHistories] = useState<InboundHistoryResponse[]>([]);
  const [rows, setRows] = useState<InboundRow[]>([
    { itemId: '', warehouseId: '', quantity: 0 },
  ]);

  useEffect(() => {
    fetchItems().then((items) =>
      setItems(items.filter((i) => i.itemType === 'RAW'))
    );

    fetchWarehouses().then(setWarehouses);
    loadHistories();
  }, []);

  const loadHistories = async () => {
    setHistories(await fetchInboundHistories());
  };

  const updateRow = (
    index: number,
    key: keyof InboundRow,
    value: number | ''
  ) => {
    const next = [...rows];
    next[index] = { ...next[index], [key]: value };
    setRows(next);
  };

  const submit = async () => {
    const row = rows[0];

    if (!row.itemId || !row.warehouseId || row.quantity <= 0) {
      alert('자재 / 창고 / 수량을 입력하세요');
      return;
    }

    try {
      await inboundStock({
        itemId: row.itemId,
        warehouseId: row.warehouseId,
        quantity: row.quantity,
      });

      setRows([{ itemId: '', warehouseId: '', quantity: 0 }]);
      await loadHistories();
    } catch (e: any) {
      alert(e.response?.data?.message ?? '입고 처리 실패');
    }
  };

  const removeInboundHistory = async (id: number) => {
    if (!confirm('입고 내역을 삭제하시겠습니까?')) return;

    try {
      await deleteInboundHistory(id);
      await loadHistories();
    } catch (e: any) {
      alert(e.response?.data?.message ?? e.message);
    }
  };
  return (
    <div className="max-w-5xl mx-auto space-y-8">
      <div className="text-center space-y-2">
        <h1 className="text-2xl font-bold">자재 입고</h1>
        <p className="text-sm text-gray-500">
          자재 및 창고를 선택하여 입고 처리합니다
          <br />
          입고 내역 삭제시 재고에 반영됩니다
        </p>
      </div>

      <div className="flex justify-center">
        <div className="relative w-full max-w-3xl">
          <div className="bg-white border border-gray-300 rounded-xl p-6">
            <table className="w-full text-sm table-fixed">
              <thead className="border-b border-gray-200 text-center text-gray-600">
                <tr>
                  <th className="py-2">자재</th>
                  <th className="py-2">창고</th>
                  <th className="py-2">단위</th>
                  <th className="py-2">단가</th>
                  <th className="py-2">수량</th>
                  <th className="py-2">금액</th>
                </tr>
              </thead>
              <tbody>
                {rows.map((row, index) => {
                  const item = items.find((i) => i.id === row.itemId);
                  const price = item?.unitPrice ?? 0;
                  const amount = Number(price) * row.quantity;

                  return (
                    <tr
                      key={index}
                      className="border-b border-gray-100 last:border-0 text-center"
                    >
                      <td className="px-2 py-2">
                        <select
                          value={row.itemId}
                          onChange={(e) =>
                            updateRow(
                              index,
                              'itemId',
                              e.target.value ? Number(e.target.value) : ''
                            )
                          }
                          className="w-full border border-gray-300 rounded-md px-2 py-1.5"
                        >
                          <option value="">자재 선택</option>
                          {items.map((i) => (
                            <option key={i.id} value={i.id}>
                              {i.itemName}
                            </option>
                          ))}
                        </select>
                      </td>

                      <td className="px-2 py-2">
                        <select
                          value={row.warehouseId}
                          onChange={(e) =>
                            updateRow(
                              index,
                              'warehouseId',
                              e.target.value ? Number(e.target.value) : ''
                            )
                          }
                          className="w-full border border-gray-300 rounded-md px-2 py-1.5"
                        >
                          <option value="">창고 선택</option>
                          {warehouses.map((w) => (
                            <option key={w.id} value={w.id}>
                              {w.warehouseName}
                            </option>
                          ))}
                        </select>
                      </td>

                      <td>{item?.unit ?? '-'}</td>
                      <td>{price ? price.toLocaleString() : '-'}</td>

                      <td>
                        <input
                          type="text"
                          value={row.quantity === 0 ? '' : row.quantity}
                          onChange={(e) =>
                            updateRow(
                              index,
                              'quantity',
                              Number(e.target.value.replace(/[^0-9]/g, '')) || 0
                            )
                          }
                          className="w-20 border border-gray-300 rounded-md text-center px-2 py-1.5"
                        />
                      </td>

                      <td>{amount.toLocaleString()}</td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
          </div>

          <button
            onClick={submit}
            className="
              absolute
              right-[-56px]
              top-1/2 -translate-y-1/2
              w-10 h-10
              rounded-full
              border border-gray-300
              bg-white
              text-xl font-semibold
              text-gray-600
              hover:bg-gray-100
            "
          >
            +
          </button>
        </div>
      </div>

      <div className="bg-white border border-gray-300 rounded-xl overflow-hidden ">
        <div className="px-5 py-3 border-b border-gray-200 font-semibold">
          입고 내역
        </div>
        <table className="w-full text-sm table-fixed">
          <thead className="bg-gray-50 border-b border-gray-200 text-center">
            <tr>
              <th className="px-5 py-3">자재</th>
              <th className="px-5 py-3">창고</th>
              <th className="px-5 py-3">단위</th>
              <th className="px-5 py-3">단가</th>
              <th className="px-5 py-3">수량</th>
              <th className="px-5 py-3">금액</th>
              <th className="px-5 py-3">입고일</th>
              <th className="px-5 py-3 w-12"></th>
            </tr>
          </thead>
          <tbody>
            {histories.length === 0 ? (
              <tr className="text-center text-gray-400">
                <td colSpan={7} className="py-6">
                  입고 내역이 없습니다
                </td>
              </tr>
            ) : (
              histories.map((h) => {
                const amount = h.unitPrice * h.changeQuantity;

                return (
                  <tr
                    key={h.createdAt + h.itemId}
                    className="text-center border-b border-gray-200 last:border-0"
                  >
                    <td className="px-5 py-3">{h.itemName}</td>
                    <td className="px-5 py-3">{h.warehouseName}</td>
                    <td className="px-5 py-3">{h.unit}</td>
                    <td className="px-5 py-3">
                      {h.unitPrice.toLocaleString()}
                    </td>
                    <td className="px-5 py-3">
                      {h.changeQuantity.toLocaleString()}
                    </td>
                    <td className="px-5 py-3">{amount.toLocaleString()}</td>
                    <td className="px-5 py-3 text-sm text-gray-500 whitespace-nowrap">
                      {new Date(h.createdAt)
                        .toISOString()
                        .slice(0, 16)
                        .replace('T', ' ')}
                    </td>
                    <td className="px-5 py-3 text-center">
                      <button
                        onClick={() => removeInboundHistory(h.id)}
                        className="text-gray-400 hover:text-red-500"
                      >
                        🗑
                      </button>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
