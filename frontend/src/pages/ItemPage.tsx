import { useEffect, useState } from "react";
import { createItem, fetchItems, deleteItem } from "../api/item";
import type { ItemCreateRequest, ItemResponse } from "../types/item";

type Tab = "RAW" | "FINISHED";

export default function ItemPage() {
  const [tab, setTab] = useState<Tab>("RAW");
  const [items, setItems] = useState<ItemResponse[]>([]);
  const [form, setForm] = useState<ItemCreateRequest>({
    itemCode: "",
    itemName: "",
    itemType: "FINISHED",
    unit: "",
    unitPrice: null,
  });

  useEffect(() => {
    fetchItems().then(setItems);
  }, []);

  useEffect(() => {
    setForm((f) => ({
      ...f,
      itemType: tab,
      itemName: "",      
      unit: tab === "RAW" ? f.unit : "",
    unitPrice: tab === "RAW" ? f.unitPrice : null,
  }));
}, [tab]);

  const submit = async () => {

    
    if (tab === "FINISHED") {
      if (!form.itemName.trim()) {
        alert("제품명을 입력하세요");
        return;
      }      
    }else{

      if (!form.itemName.trim()) {
        alert("자재명을 입력하세요");
        return;
      }      
      if (!form.unit?.trim()) {
        alert("단위를 입력하세요");
        return;
      }
      if (form.unitPrice == null || form.unitPrice <= 0) {
        alert("단가는 0보다 커야 합니다");
        return;
      }
    }
    
    await createItem(form);
    setItems(await fetchItems());
    setForm((f) => ({
      ...f,
      itemName: "",
      unit: "",
      unitPrice: null,
    }));
  };

  const removeItem = async (id: number) => {
    if (!confirm("삭제하시겠습니까?")) return;

    try{
      await deleteItem(id);
      setItems(await fetchItems());
    }catch(e:any){
      alert(e.response?.data?.message ?? e.message);
    }

};

  const isFinished = tab === "FINISHED";

  return (
    <div className="max-w-4xl mx-auto space-y-8">
      <div className="text-center space-y-2">
        <h1 className="text-2xl font-bold">자재 / 품목 등록</h1>
        <h2 className="text-sm text-gray-500 font-normal">
          재고 관리를 위한 자재와 품목을 등록하는 화면입니다
          <br />
          (입고 및 생산내역 있을 시 삭제 불가)
        </h2>
      </div>

      <div className="flex justify-center">
        <div className="w-full max-w-md bg-white border border-gray-300 rounded-lg flex overflow-hidden">
          <TabButton active={tab === "RAW"} onClick={() => setTab("RAW")}>
            자재
          </TabButton>
          <TabButton active={tab === "FINISHED"} onClick={() => setTab("FINISHED")}>
            품목
          </TabButton>
        </div>
      </div>

      <div className="mx-auto max-w-md relative">
        <div className="bg-white border border-gray-300 rounded-xl p-6">
          <div className="grid gap-4">
            <Field
              label={isFinished ? "품목명" : "자재명"}
              value={form.itemName}
              onChange={(e) =>
                setForm({ ...form, itemName: e.target.value })
              }
            />

            {!isFinished && (
              <>
                <Field
                  label="단위"
                  value={form.unit}
                  onChange={(e) =>
                    setForm({ ...form, unit: e.target.value })
                  }
                />
                <Field
                  label="단가"              
                  type="number"  
                  value={form.unitPrice ?? ""}
                  onChange={(e) =>
                    setForm({
                      ...form,
                      unitPrice: e.target.value === "" ? null : Number(e.target.value),
                    })
                  }
                />
              </>
            )}
          </div>
        </div>

        <button
          type="button"
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
            transition
          "
          onClick={submit}>
          +
        </button>
      </div>

      <div className="bg-white border border-gray-300 rounded-xl overflow-hidden">
      <table className="w-full text-sm table-fixed">
          <colgroup>
            <col />             
            <col />             
            {!isFinished && <col />}
            {!isFinished && <col />} 
            <col className="w-12" /> 
          </colgroup>        
          <thead className="bg-gray-50 border-b border-gray-200">
            <tr>
              <th className="px-5 py-3 text-left">
                {isFinished ? "품목명" : "자재명"}
              </th>

              <th className="px-5 py-3 text-left">코드</th>

              {!isFinished && (
                <>
                  <th className="px-5 py-3 text-left">단위</th>
                  <th className="px-5 py-3 text-left">단가</th>
                </>
              )}
              <th className="px-5 py-3"></th>
            </tr>
          </thead>

          <tbody>
            {items
              .filter((item) => item.itemType === tab)
              .map((item) => (
                <tr
                  key={item.id}
                  className="border-b border-gray-100 hover:bg-gray-50"
                >
                  <td className="px-5 py-3">{item.itemName}</td>

                  <td className="px-5 py-3">
                    {item.itemCode}
                  </td>

                  {!isFinished && (
                    <>
                      <td className="px-5 py-3">{item.unit}</td>
                      <td className="px-5 py-3">
                        {item.unitPrice?.toLocaleString()}
                      </td>
                    </>
                  )}
                <td className="px-5 py-3 text-center">
                  <button
                  onClick={()=>removeItem(item.id)}
                    className="text-gray-400 hover:text-red-500"
                  >
                    🗑
                  </button>
                </td>                  
                </tr>
              ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

function TabButton({
  active,
  onClick,
  children,
}: {
  active: boolean;
  onClick: () => void;
  children: React.ReactNode;
}) {
  return (
    <button
      onClick={onClick}
      className={`
        flex-1 h-12 text-sm font-semibold
        ${active ? "bg-blue-200 text-blue-900" : "text-gray-500 hover:bg-gray-100"}
      `}
    >
      {children}
    </button>
  );
}

function Field({
  label,
  ...props
}: React.InputHTMLAttributes<HTMLInputElement> & { label: string }) {
  return (
    <label className="space-y-1">
      <div className="text-xs font-medium text-gray-600">{label}</div>
      <input
        {...props}
        className="w-full rounded-md border border-gray-300 px-3 py-2 text-sm"
      />
    </label>
  );
}