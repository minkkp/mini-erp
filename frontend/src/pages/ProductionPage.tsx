import { useEffect, useState } from 'react';
import { fetchItems } from '../api/item';
import { fetchStocks } from '../api/stock';
import { fetchWarehouses } from '../api/warehouse';
import { fetchProductions } from '../api/production';
import { createProduction } from '../api/production';
import type { ItemResponse } from '../types/item';
import type { StockResponse } from '../types/stock';
import type { WarehouseResponse } from '../types/warehouse';
import type { ProductionResponse } from '../types/production';

type MaterialRow = {
  stockId: number;
  quantity: number;
};

export default function ProductionPage() {
  const [products, setProducts] = useState<ItemResponse[]>([]);
  const [stocks, setStocks] = useState<StockResponse[]>([]);
  const [productId, setProductId] = useState<string>('');
  const [warehouses, setWarehouses] = useState<WarehouseResponse[]>([]);
  const [warehouseId, setWarehouseId] = useState<string>('');
  const [goodQty, setGoodQty] = useState(0);
  const [badQty, setBadQty] = useState(0);
  const [workMinutes, setWorkMinutes] = useState(0);
  const [history, setHistory] = useState<ProductionResponse[]>([]);

  const [rows, setRows] = useState<MaterialRow[]>([
    { stockId: 0, quantity: 0 },
  ]);

  const addRow = () => {
    setRows([...rows, { stockId: 0, quantity: 0 }]);
  };

  const updateRow = (
    index: number,
    key: keyof MaterialRow,
    value: string | number
  ) => {
    const next = [...rows];
    next[index] = { ...next[index], [key]: value };
    setRows(next);
  };

  const submit = async () => {
    if (!productId) return alert('품목 선택');
    if (rows.some((r) => !r.stockId || r.quantity <= 0)) {
      return alert('자재 입력 확인');
    }

    const payload = {
      productItemId: Number(productId),
      warehouseId: Number(warehouseId),
      goodQuantity: goodQty,
      badQuantity: badQty,
      workMinutes,
      producedAt: new Date().toISOString(),
      materials: rows.map((r) => ({
        stockId: r.stockId,
        quantity: r.quantity,
      })),
    };
    try {
      await createProduction(payload);

      setProductId('');
      setWarehouseId('');
      setGoodQty(0);
      setBadQty(0);
      setWorkMinutes(0);
      setRows([{ stockId: 0, quantity: 0 }]);
      setHistory(await fetchProductions());
      setStocks((await fetchStocks()).filter((s) => s.itemType === 'RAW'));
    } catch (e: any) {
      alert(e.response?.data?.message);
    }
  };

  useEffect(() => {
    fetchItems().then((items) =>
      setProducts(items.filter((i) => i.itemType === 'FINISHED'))
    );

    fetchStocks().then((stocks) =>
      setStocks(stocks.filter((s) => s.itemType === 'RAW'))
    );

    fetchWarehouses().then(setWarehouses);

    fetchProductions().then(setHistory);
  }, []);

  return (
    <div className="max-w-7xl mx-auto space-y-12 py-10">
      <div className="text-center space-y-2">
        <h1 className="text-2xl font-bold">생산 실적</h1>
        <p className="text-sm text-gray-500">
          자재를 소요하여 품목을 생산합니다.
        </p>
      </div>

      <div className="grid grid-cols-[minmax(0,1fr)_minmax(0,1fr)] gap-8">
        <div className="space-y-12">
          <div className="space-y-3">
            <div className="text-center font-semibold">생산 품목</div>
            <div className="bg-white border border-gray-200 rounded-xl p-6">
              <table className="w-full text-sm table-fixed border-separate border-spacing-y-3">
                <colgroup>
                  <col className="w-[22%]" />
                  <col className="w-[22%]" />
                  <col className="w-[15%]" />
                  <col className="w-[15%]" />
                  <col className="w-[15%]" />
                  <col className="w-[15%]" />
                </colgroup>
                <thead className="text-center text-gray-500">
                  <tr>
                    <th className="pb-2">품목명</th>
                    <th className="pb-2">창고</th>
                    <th className="pb-2">생산 수량</th>
                    <th className="pb-2">양품 수량</th>
                    <th className="pb-2">불량 수량</th>
                    <th className="pb-2 whitespace-nowrap">작업 시간</th>
                  </tr>
                </thead>
                <tbody>
                  <tr className="text-center">
                    <td className="px-2">
                      <select
                        value={productId}
                        onChange={(e) => setProductId(e.target.value)}
                        className="w-full border border-gray-300 rounded-md px-3 py-2"
                      >
                        <option value="">품목 선택</option>
                        {products.map((p) => (
                          <option key={`item-${p.id}`} value={p.id}>
                            {p.itemName}
                          </option>
                        ))}
                      </select>
                    </td>
                    <td className="px-2">
                      <select
                        value={warehouseId}
                        onChange={(e) => setWarehouseId(e.target.value)}
                        className="w-full border border-gray-300 rounded-md px-3 py-2"
                      >
                        <option value="">창고 선택</option>
                        {warehouses.map((w) => (
                          <option key={`warehouse-${w.id}`} value={w.id}>
                            {w.warehouseName}
                          </option>
                        ))}
                      </select>
                    </td>
                    <td className="px-2">
                      <input
                        type="text"
                        value={goodQty - badQty}
                        disabled
                        className="w-full border border-gray-300 rounded-md px-3 py-2 text-sm text-center bg-gray-100 text-gray-500 cursor-not-allowed"
                      />
                    </td>
                    <td className="px-2">
                      <InputFull value={goodQty} onChange={setGoodQty} />
                    </td>
                    <td className="px-2">
                      <InputFull value={badQty} onChange={setBadQty} />
                    </td>
                    <td className="px-2">
                      <InputFull
                        value={workMinutes}
                        onChange={setWorkMinutes}
                      />
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <div className="space-y-4">
            <div className="text-center font-semibold">투입 자재</div>
            <div className="bg-white border border-gray-200 rounded-xl p-6">
              <table className="w-full text-sm table-fixed border-separate border-spacing-y-3">
                <colgroup>
                  <col className="w-[70%]" />
                  <col className="w-[30%]" />
                </colgroup>
                <thead className="text-center text-gray-500">
                  <tr>
                    <th className="pb-2">자재명</th>
                    <th className="pb-2">수량</th>
                  </tr>
                </thead>
                <tbody>
                  {rows.map((row, i) => (
                    <tr key={i} className="text-center">
                      <td className="px-2">
                        <select
                          value={row.stockId}
                          onChange={(e) =>
                            updateRow(i, 'stockId', e.target.value)
                          }
                          className="w-full border border-gray-300 rounded-md px-3 py-2"
                        >
                          <option value="">자재 선택</option>
                          {stocks
                            .filter((s) => s.quantity > 0)
                            .map((s) => (
                              <option key={`materials` + s.id} value={s.id}>
                                {s.itemName} ({s.warehouseName}) - {s.quantity}
                              </option>
                            ))}
                        </select>
                      </td>
                      <td className="px-2">
                        <InputFull
                          value={row.quantity}
                          onChange={(v) => updateRow(i, 'quantity', v)}
                        />
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <button
                onClick={addRow}
                className="mt-4 w-full rounded-md border border-gray-300 py-2 text-sm text-gray-600 hover:bg-gray-100"
              >
                행 추가
              </button>
            </div>

            <div className="flex justify-center">
              <button
                onClick={submit}
                className="px-16 py-3 rounded-lg bg-indigo-500 text-white text-sm font-semibold hover:bg-indigo-600"
              >
                생산
              </button>
            </div>
          </div>
        </div>

        <div className="space-y-3">
          <div className="text-center font-semibold">생산 실적 내역</div>
          <div className="bg-white max-h-[450px] border border-gray-300 rounded-xl overflow-y-auto">
            <table className="w-full text-sm table-fixed">
              <colgroup>
                <col className="w-[20%]" />
                <col className="w-[15%]" />
                <col className="w-[15%]" />
                <col className="w-[25%]" />
                <col className="w-[30%]" />
              </colgroup>
              <thead className="bg-gray-50 border-b border-gray-200 text-center text-gray-600">
                <tr>
                  <th className="px-5 py-3">품목명</th>
                  <th className="px-5 py-3">양품</th>
                  <th className="px-5 py-3">불량</th>
                  <th className="px-5 py-3">작업 시간</th>
                  <th className="px-5 py-3">생산일</th>
                </tr>
              </thead>
              <tbody>
                {history.length === 0 ? (
                  <tr>
                    <td colSpan={5} className="py-6 text-center text-gray-400">
                      생산 실적이 없습니다
                    </td>
                  </tr>
                ) : (
                  history.map((h) => (
                    <tr
                      key={h.id}
                      className="border-b border-gray-100 last:border-0 hover:bg-gray-50 text-center"
                    >
                      <td className="px-5 py-3">{h.productItemName}</td>
                      <td className="px-5 py-3">{h.goodQuantity}</td>
                      <td className="px-5 py-3">{h.badQuantity}</td>
                      <td className="px-5 py-3">{h.workMinutes}</td>
                      <td className="px-5 py-3 text-sm text-gray-500 whitespace-nowrap">
                        {new Date(h.producedAt)
                          .toISOString()
                          .slice(0, 16)
                          .replace('T', ' ')}
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

function InputFull({
  value,
  onChange,
}: {
  value: number;
  onChange: (v: number) => void;
}) {
  return (
    <input
      type="text"
      value={value}
      onChange={(e) =>
        onChange(Number(e.target.value.replace(/[^0-9]/g, '')) || 0)
      }
      className="w-full border border-gray-300 rounded-md px-3 py-2 text-sm text-center"
    />
  );
}
