import { useEffect, useState } from "react";
import API from "../services/api";
import Payment from "./Payment";

function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [couponCode, setCouponCode] = useState("");
  const [finalPrice, setFinalPrice] = useState(null);
  const [discount, setDiscount] = useState(0);
  const [message, setMessage] = useState("");

  useEffect(() => {
    loadCart();
  }, []);

  const loadCart = () => {
    const userId = localStorage.getItem("userId");

    API.get(`/cart/${userId}`)
      .then((res) => {
        const backendItems = res.data.cartItems || [];
        const comboCart =
          JSON.parse(localStorage.getItem("cart")) || [];

        setCartItems([...backendItems, ...comboCart]);
      })
      .catch((err) => console.log(err));
  };

  // ✅ PIZZA
  const increase = (item) => {
    const userId = localStorage.getItem("userId");

    API.post("/cart", {
      userId,
      pizzaId: item.pizza.id,
      quantity: item.quantity + 1,
    }).then(() => loadCart());
  };

  const decrease = (item) => {
    const userId = localStorage.getItem("userId");

    if (item.quantity === 1) {
      removeItem(item);
      return;
    }

    API.post("/cart", {
      userId,
      pizzaId: item.pizza.id,
      quantity: item.quantity - 1,
    }).then(() => loadCart());
  };

  const removeItem = (item) => {
    API.delete(`/cart/item/${item.id}`).then(() =>
      loadCart()
    );
  };

  // ✅ COMBO
  const increaseCombo = (item) => {
    let comboCart =
      JSON.parse(localStorage.getItem("cart")) || [];

    comboCart = comboCart.map((c) =>
      c.id === item.id
        ? { ...c, quantity: c.quantity + 1 }
        : c
    );

    localStorage.setItem("cart", JSON.stringify(comboCart));
    loadCart();
  };

  const decreaseCombo = (item) => {
    let comboCart =
      JSON.parse(localStorage.getItem("cart")) || [];

    comboCart = comboCart
      .map((c) =>
        c.id === item.id
          ? { ...c, quantity: c.quantity - 1 }
          : c
      )
      .filter((c) => c.quantity > 0);

    localStorage.setItem("cart", JSON.stringify(comboCart));
    loadCart();
  };

  const removeCombo = (item) => {
    let comboCart =
      JSON.parse(localStorage.getItem("cart")) || [];

    comboCart = comboCart.filter(
      (c) => c.id !== item.id
    );

    localStorage.setItem("cart", JSON.stringify(comboCart));
    loadCart();
  };

  // ✅ TOTAL
  const getTotal = () => {
    return cartItems.reduce((total, item) => {
      const price = item.pizza
        ? item.pizza.price
        : item.price;

      return total + price * item.quantity;
    }, 0);
  };

  // ✅ APPLY COUPON
  const applyCoupon = () => {
    const userId = localStorage.getItem("userId");

    API.post("/coupons/apply", {
      userId,
      code: couponCode,
    })
      .then((res) => {
        setFinalPrice(res.data.finalAmount);
        setDiscount(res.data.discountApplied);
        setMessage("✅ Coupon Applied Successfully!");
      })
      .catch(() => {
        setMessage("❌ Invalid or Not Eligible Coupon");
      });
  };

  // ✅ BEST COUPON
  const applyBestCoupon = () => {
    const total = getTotal();

    let bestCode = "";
    let bestDiscount = 0;

    if (total >= 1000) {
      bestCode = "FIRST50";
      bestDiscount = total * 0.5;
    } else if (total >= 500) {
      bestCode = "SAVE20";
      bestDiscount = total * 0.2;
    } else if (total >= 200) {
      bestCode = "SAVE10";
      bestDiscount = total * 0.1;
    }

    if (bestCode) {
      setCouponCode(bestCode);
      setFinalPrice(total - bestDiscount);
      setDiscount(bestDiscount);
      setMessage("🎯 Best Coupon Applied: " + bestCode);
    }
  };

  const coupons = [
    { code: "SAVE10", desc: "10% OFF above ₹200" },
    { code: "SAVE20", desc: "20% OFF above ₹500" },
    { code: "FIRST50", desc: "50% OFF above ₹1000" },
  ];

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4">🛒 Cart</h2>

      {cartItems.length === 0 ? (
        <h5 className="text-center text-muted">
          Cart is empty ❌
        </h5>
      ) : (
        <>
          {cartItems.map((item, index) => (
            <div
              key={index}
              className="card p-3 mb-3 shadow d-flex justify-content-between align-items-center flex-row"
            >
              <div>
                <h5>
                  {item.pizza
                    ? item.pizza.name
                    : item.name}
                </h5>

                <p>
                  ₹
                  {(
                    item.pizza
                      ? item.pizza.price
                      : item.price
                  ).toFixed(2)}
                </p>

                <span className="badge bg-secondary">
                  {item.pizza ? "PIZZA" : "COMBO"}
                </span>
              </div>

              <div>
                <button
                  className="btn btn-outline-secondary"
                  onClick={() =>
                    item.pizza
                      ? decrease(item)
                      : decreaseCombo(item)
                  }
                >
                  -
                </button>

                <span className="mx-3 fw-bold">
                  {item.quantity}
                </span>

                <button
                  className="btn btn-outline-secondary"
                  onClick={() =>
                    item.pizza
                      ? increase(item)
                      : increaseCombo(item)
                  }
                >
                  +
                </button>
              </div>

              <button
                className="btn btn-outline-danger"
                onClick={() =>
                  item.pizza
                    ? removeItem(item)
                    : removeCombo(item)
                }
              >
                Remove
              </button>
            </div>
          ))}

          <h4 className="text-center mt-4">
            Total: ₹{getTotal().toFixed(2)}
          </h4>

          {/* ✅ COUPON CARDS */}
          <div className="mt-4 text-center">
            <h5>Available Coupons 🎉</h5>

            <div className="d-flex justify-content-center gap-3 flex-wrap mt-2">
              {coupons.map((c, i) => (
                <div
                  key={i}
                  className="p-3 border border-dashed rounded shadow-sm"
                  style={{ cursor: "pointer" }}
                  onClick={() => setCouponCode(c.code)}
                >
                  <h6 className="text-success">{c.code}</h6>
                  <small>{c.desc}</small>
                </div>
              ))}
            </div>
          </div>

          {/* ✅ APPLY COUPON */}
          <div className="text-center mt-4">
            <input
              className="form-control w-50 mx-auto text-center"
              placeholder="Enter coupon code"
              value={couponCode}
              onChange={(e) =>
                setCouponCode(e.target.value)
              }
            />

            <button
              className="btn btn-dark mt-2 me-2"
              onClick={applyCoupon}
            >
              Apply Coupon
            </button>

            <button
              className="btn btn-warning mt-2"
              onClick={applyBestCoupon}
            >
              🎯 Best Coupon
            </button>

            <p className="mt-2">{message}</p>

            {finalPrice && (
              <div
                className="mt-3 p-3"
                style={{
                  background: "#eaffea",
                  borderRadius: "10px",
                }}
              >
                <h5 className="text-success">
                  🎉 You saved ₹{discount.toFixed(2)}
                </h5>

                <p style={{ textDecoration: "line-through" }}>
                  ₹{getTotal().toFixed(2)}
                </p>

                <h3>
                  Final: ₹{finalPrice.toFixed(2)}
                </h3>
              </div>
            )}
          </div>

          <div className="text-center mt-4">
            <Payment
              totalAmount={finalPrice || getTotal()}
            />
          </div>
        </>
      )}
    </div>
  );
}

export default Cart;
