import type { Page } from '../App';

export default function Layout({
  page,
  onSelect,
  children,
}: {
  page: Page;
  onSelect: (p: Page) => void;
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen flex bg-gray-100">
      <aside className="w-64 bg-white border-r px-4 py-6 flex flex-col">
        <button
          onClick={() => onSelect('MENU')}
          className="mb-8 flex items-center gap-2 text-blue-600 hover:text-blue-700"
          title="메인으로"
        >
          <HomeIcon />
        </button>
        <nav className="space-y-1">
          <SideItem
            label="자재/품목 등록"
            active={page === 'ITEM'}
            onClick={() => onSelect('ITEM')}
          />
          <SideItem
            label="자재 입고"
            active={page === 'INBOUND'}
            onClick={() => onSelect('INBOUND')}
          />
          <SideItem
            label="생산 실적"
            active={page === 'PRODUCTION'}
            onClick={() => onSelect('PRODUCTION')}
          />
          <SideItem
            label="재고 조회"
            active={page === 'STOCK'}
            onClick={() => onSelect('STOCK')}
          />
        </nav>
      </aside>
      <main className="flex-1 p-8">{children}</main>
    </div>
  );
}

function SideItem({
  label,
  active,
  onClick,
}: {
  label: string;
  active: boolean;
  onClick: () => void;
}) {
  return (
    <button
      onClick={onClick}
      className={`w-full text-left px-3 py-2 rounded transition
        ${
          active
            ? 'bg-blue-100 text-blue-700 font-semibold'
            : 'hover:bg-gray-100 text-gray-700'
        }
      `}
    >
      {label}
    </button>
  );
}

function HomeIcon() {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      className="w-7 h-7"
      fill="none"
      viewBox="0 0 24 24"
      stroke="currentColor"
      strokeWidth={2}
    >
      <path
        strokeLinecap="round"
        strokeLinejoin="round"
        d="M3 10.5L12 3l9 7.5M5 10v9a1 1 0 001 1h4v-6h4v6h4a1 1 0 001-1v-9"
      />
    </svg>
  );
}
