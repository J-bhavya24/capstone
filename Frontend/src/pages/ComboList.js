import React, { useEffect, useState } from "react";
import API from "../services/api";

const ComboList = () => {
  const [combos, setCombos] = useState([]);

  // ✅ FETCH COMBOS
  useEffect(() => {
    API.get("/combos")
      .then((res) => {
        console.log("Combos:", res.data);
        setCombos(res.data);
      })
      .catch((err) => console.log(err));
  }, []);

  // ✅ ADD TO CART
  const addToCart = (combo) => {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];

    const existing = cart.find(
      (item) => item.id === "combo-" + combo.id
    );

    if (existing) {
      existing.quantity += 1;
    } else {
      cart.push({
        id: "combo-" + combo.id,
        name: combo.name,
        price: combo.discountedPrice,
        quantity: 1,
        type: "COMBO",
      });
    }

    localStorage.setItem("cart", JSON.stringify(cart));

    alert("✅ Combo added to cart!");
  };

  return (
    <div className="container mt-4">
      <h2>🔥 Combo Offers</h2>

      <div className="row mt-3">
        {combos.length === 0 ? (
          <h5>No combos available ❌</h5>
        ) : (
          combos.map((combo) => (
            <div className="col-md-4 mb-4" key={combo.id}>
              <div className="card h-100 shadow">

                {/* ✅ ✅ IMAGE FIX (VERY IMPORTANT) */}
                <img
                  src={combo.imageUrl}
                  alt="combo"
                  className="card-img-top"
                  style={{ height: "200px", objectFit: "cover" }}
                  onError={(e) => {
                    e.target.src =
                      "https://via.placeholder.com/300x200?text=No+Image";
                  }}
                />

                <div className="card-body">
                  <h5>{combo.name} 🍕🍕</h5>
                  <p>{combo.description}</p>

                  {/* ✅ COMBO ITEMS */}
                  <ul>
                    {combo.pizzas.map((p) => (
                      <li key={p.id}>{p.name}</li>
                    ))}
                  </ul>

                  {/* ✅ PRICE */}
                  <p>
                    <span
                      className="text-danger"
                      style={{ textDecoration: "line-through" }}
                    >
                      ₹{combo.originalPrice?.toFixed(2)}
                    </span>{" "}
                    <span className="text-success fw-bold">
                      ₹{combo.discountedPrice?.toFixed(2)}
                    </span>
                  </p>

                  {/* ✅ SAVE */}
                  <span className="badge bg-success">
                    Save ₹
                    {(combo.originalPrice - combo.discountedPrice)?.toFixed(2)}
                  </span>

                  {/* ✅ BUTTON */}
                  <button
                    className="btn btn-warning w-100 mt-3"
                    onClick={() => addToCart(combo)}
                  >
                    Add Combo
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default ComboList;