import { useState } from "react";
import MainMenu from "./components/MainMenu";
import Layout from "./components/Layout";
import ItemPage from "./pages/ItemPage";
import InboundPage from "./pages/InboundPage";
import StockPage from "./pages/StockPage";
import ProductionPage from "./pages/ProductionPage";

export type Page =
  | "MENU"
  | "ITEM"
  | "INBOUND"
  | "PRODUCTION"
  | "STOCK";

export default function App() {
  const [page, setPage] = useState<Page>("MENU");

  if (page === "MENU") {
    return <MainMenu onSelect={setPage} />;
  }

  return (
    <Layout page={page} onSelect={setPage}>
      {page === "ITEM" && <ItemPage />}
      {page === "INBOUND" && <InboundPage/>}
      {page === "PRODUCTION" && <ProductionPage/>}
      {page === "STOCK" && <StockPage/>}
    </Layout>
  );
}
