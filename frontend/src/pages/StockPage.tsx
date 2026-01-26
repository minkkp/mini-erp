import { useEffect, useState } from 'react';
import { fetchStockSummary } from '../api/stock';
import type { StockSummaryResponse } from '../types/stock';

export default function StockPage() {
  const [stocks, setStocks] = useState<StockSummaryResponse[]>([]);

  useEffect(() => {
    fetchStockSummary().then(setStocks);
  }, []);

  const finished = stocks.filter((s) => s.itemType === 'FINISHED');
  const raw = stocks.filter((s) => s.itemType === 'RAW');

  return (
    <div className="max-w-6xl mx-auto space-y-10">
      <div className="text-center space-y-2">
        <h1 className="text-2xl font-bold">재고 조회</h1>
        <p className="text-sm text-gray-500">
          자재 입고 및 생산 실적에 반영된 재고 현황입니다.
        </p>
      </div>

      <div className="grid grid-cols-2 gap-10">
        <div className="space-y-3">
          <div className="text-lg font-bold text-gray-800 text-center">
            품목
          </div>
          <div className="bg-white border border-gray-300 rounded-xl overflow-hidden">
            <table className="w-full text-sm table-fixed">
              <thead className="bg-gray-50 border-b border-gray-200 text-center">
                <tr>
                  <th className="px-5 py-3">품목명</th>
                  <th className="px-5 py-3">재고 수량</th>
                </tr>
              </thead>
              <tbody>
                {finished.length === 0 ? (
                  <tr>
                    <td colSpan={2} className="py-6 text-center text-gray-400">
                      데이터가 없습니다
                    </td>
                  </tr>
                ) : (
                  finished.map((s, i) => (
                    <tr
                      key={i}
                      className="border-b border-gray-100 last:border-0 hover:bg-gray-50 text-center"
                    >
                      <td className="px-5 py-3">{s.itemName}</td>
                      <td className="px-5 py-3">
                        {s.quantity.toLocaleString()}
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>

        <div className="space-y-3">
          <div className="text-lg font-bold text-gray-800 text-center">
            자재
          </div>
          <div className="bg-white border border-gray-300 rounded-xl overflow-hidden">
            <table className="w-full text-sm table-fixed">
              <thead className="bg-gray-50 border-b border-gray-200 text-center">
                <tr>
                  <th className="px-5 py-3">자재명</th>
                  <th className="px-5 py-3">입고 수량</th>
                  <th className="px-5 py-3">출고 수량</th>
                  <th className="px-5 py-3">재고 수량</th>
                </tr>
              </thead>
              <tbody>
                {raw.length === 0 ? (
                  <tr>
                    <td colSpan={4} className="py-6 text-center text-gray-400">
                      데이터가 없습니다
                    </td>
                  </tr>
                ) : (
                  raw.map((s, i) => (
                    <tr
                      key={i}
                      className="border-b border-gray-100 last:border-0 hover:bg-gray-50 text-center"
                    >
                      <td className="px-5 py-3">{s.itemName}</td>
                      <td className="px-5 py-3">
                        {s.inboundQty.toLocaleString()}
                      </td>
                      <td className="px-5 py-3">
                        {s.outboundQty.toLocaleString()}
                      </td>
                      <td className="px-5 py-3">
                        {s.quantity.toLocaleString()}
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
