import { useEffect, useState } from "react";
import API from "../services/api";
import PizzaList from "../components/PizzaList";

function Home() {

  const [allPizzas, setAllPizzas] = useState([]);
  const [randomPizzas, setRandomPizzas] = useState([]);
  const [topPricePizzas, setTopPricePizzas] = useState([]);
  const [recentPizzas, setRecentPizzas] = useState([]);

  const [search, setSearch] = useState("");
  const [debouncedSearch, setDebouncedSearch] = useState("");

  // ✅ DEBOUNCE SEARCH
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedSearch(search);
    }, 300);

    return () => clearTimeout(timer);
  }, [search]);

  // ✅ LOAD DATA
  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const res = await API.get("/pizzas");
      const data = res.data;

      setAllPizzas(data);

      // ✅ RANDOM
      const random = [...data]
        .sort(() => 0.5 - Math.random())
        .slice(0, 3);

      setRandomPizzas(random);

      // ✅ TOP PRICE
      const top = [...data]
        .sort((a, b) => b.price - a.price)
        .slice(0, 3);

      setTopPricePizzas(top);

      // ✅ RECENT
      const recent = [...data]
        .sort((a, b) => b.id - a.id)
        .slice(0, 3);

      setRecentPizzas(recent);

    } catch (err) {
      console.error(err);
    }
  };

  // ✅ SEARCH FILTER ONLY
  const filteredPizzas = allPizzas.filter(p =>
    p.name.toLowerCase().includes(debouncedSearch.toLowerCase())
  );

  return (
    <div className="container mt-4">

      {/* 🔍 SEARCH */}
      <input
        type="text"
        className="form-control mb-4"
        placeholder="🔍 Search Pizza..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {/* ✅ SHOW SEARCH RESULTS */}
      {debouncedSearch ? (
        <>
          <h3>🔍 Search Results</h3>
          <PizzaList pizzasProp={filteredPizzas} />
        </>
      ) : (
        <>
          {/* ✅ RANDOM */}
          <h3>🎯 Recommended</h3>
          <PizzaList pizzasProp={randomPizzas} />

          {/* ✅ TOP PRICE */}
          <h3>💎 Premium Pizzas</h3>
          <PizzaList pizzasProp={topPricePizzas} />

          {/* ✅ RECENT */}
          <h3>🆕 Recently Added</h3>
          <PizzaList pizzasProp={recentPizzas} />

          {/* ✅ ALL */}
          <h3 className="text-center mt-4">🍕 All Pizzas</h3>
          <PizzaList pizzasProp={allPizzas} />
        </>
      )}

    </div>
  );
}

export default Home;
