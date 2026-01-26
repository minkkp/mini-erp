import type { Page } from '../App';

export default function MainMenu({
  onSelect,
}: {
  onSelect: (p: Page) => void;
}) {
  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-100 to-gray-200 flex items-center justify-center">
      <div className="bg-white w-full max-w-md rounded-xl shadow-lg p-8">
        <h1 className="text-2xl font-bold text-center mb-2">MINI ERP</h1>
        <p className="text-center text-gray-500 mb-8">
          간단한 재고 · 생산 관리 시스템
        </p>

        <div className="grid gap-4">
          <MenuButton
            title="자재 / 품목 등록"
            desc="제품 및 원자재 마스터 관리"
            onClick={() => onSelect('ITEM')}
          />
          <MenuButton
            title="자재 입고"
            desc="창고별 자재 입고 처리"
            onClick={() => onSelect('INBOUND')}
          />
          <MenuButton
            title="생산 실적"
            desc="자재 소요 및 완제품 생산"
            onClick={() => onSelect('PRODUCTION')}
          />
          <MenuButton
            title="재고 조회"
            desc="현재 재고 현황 확인"
            onClick={() => onSelect('STOCK')}
          />
        </div>
      </div>
    </div>
  );
}

function MenuButton({
  title,
  desc,
  onClick,
}: {
  title: string;
  desc: string;
  onClick?: () => void;
}) {
  return (
    <button
      onClick={onClick}
      disabled={!onClick}
      className={`w-full text-left p-4 rounded-lg border transition
      ${
        onClick
          ? 'bg-white hover:bg-blue-50 border-blue-200'
          : 'bg-gray-100 text-gray-400'
      }
    `}
    >
      <div className="font-semibold">{title}</div>
      <div className="text-sm mt-1">{desc}</div>
    </button>
  );
}
