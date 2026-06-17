import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import axios from "axios";

function Navbar() {

  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  const [open, setOpen] = useState(false);
  const [showDelivery, setShowDelivery] = useState(false);

  const location = useLocation();

  const logout = () => {
    localStorage.clear();
    window.location.href = "/";
  };

  // ✅ CHECK DELIVERY STATUS
  useEffect(() => {

    const checkOrders = async () => {
      try {
        const userId = localStorage.getItem("userId");

        if (!token || !userId) return;

        const res = await axios.get(
          `http://localhost:8080/api/orders/my/${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );

        const activeOrder = res.data.some(
          order => order.status !== "DELIVERED"
        );

        setShowDelivery(activeOrder);

      } catch (error) {
        console.log("Delivery check error:", error);
      }
    };

    checkOrders();

  }, [token]);

  return (
    <nav className="navbar navbar-dark bg-dark px-4">

      {/* ✅ LOGO */}
      <Link to="/" className="navbar-brand text-white fw-bold">
        🍕 Pizza App
      </Link>

      <div>

        {/* ✅ NOT LOGGED */}
        {!token && (
          <>
            <Link to="/login" className="btn btn-warning me-2">
              Login
            </Link>

            <Link to="/register" className="btn btn-warning">
              Register
            </Link>
          </>
        )}

        {/* ✅ CUSTOMER + ADMIN */}
        {token && (role === "CUSTOMER" || role === "ADMIN") && (
          <>

            {/* ✅ GOLD BUTTON STYLE */}
            <Link
              to="/home"
              className={`btn btn-outline-warning me-2 ${
                location.pathname === "/home" && "bg-warning text-dark"
              }`}
            >
              Home
            </Link>

            <Link
              to="/cart"
              className={`btn btn-outline-warning me-2 ${
                location.pathname === "/cart" && "bg-warning text-dark"
              }`}
            >
              Cart
            </Link>

            <Link
              to="/orders"
              className={`btn btn-outline-warning me-2 ${
                location.pathname === "/orders" && "bg-warning text-dark"
              }`}
            >
              Orders
            </Link>

            <Link
              to="/combos"
              className={`btn btn-outline-warning me-2 ${
                location.pathname === "/combos" && "bg-warning text-dark"
              }`}
            >
              🔥 Combos
            </Link>

            {/* ✅ DELIVERY BUTTON */}
            {showDelivery && (
              <Link
                to="/delivery"
                className={`btn btn-outline-warning me-2 ${
                  location.pathname === "/delivery" && "bg-warning text-dark"
                }`}
              >
                🚚 Delivery
              </Link>
            )}

            {!showDelivery && (
              <span className="text-success me-2">
                ✅ No active delivery
              </span>
            )}

            {/* ✅ ADMIN */}
            {role === "ADMIN" && (
              <Link to="/admin" className="btn btn-warning me-3">
                Admin
              </Link>
            )}

            {/* ✅ PROFILE */}
            <div className="d-inline position-relative">

              <button
                className="btn btn-warning"
                onClick={() => setOpen(!open)}
              >
                👤 {role} ▼
              </button>

              {open && (
                <div
                  className="position-absolute bg-dark text-warning p-2 shadow"
                  style={{
                    right: 0,
                    top: "45px",
                    borderRadius: "8px",
                    minWidth: "150px"
                  }}
                >
                  <p className="m-1">👤 {role}</p>
                  <hr />

                  <button
                    className="btn btn-danger btn-sm w-100"
                    onClick={logout}
                  >
                    Logout
                  </button>
                </div>
              )}

            </div>

          </>
        )}

        {/* ✅ DELIVERY AGENT */}
        {token && role === "DELIVERY" && (
          <>
            <Link
              to="/delivery-dashboard"
              className="btn btn-warning me-3"
            >
              Delivery Dashboard
            </Link>

            <div className="d-inline position-relative">

              <button
                className="btn btn-warning"
                onClick={() => setOpen(!open)}
              >
                👤 DELIVERY ▼
              </button>

              {open && (
                <div
                  className="position-absolute bg-dark text-warning p-2 shadow"
                  style={{
                    right: 0,
                    top: "45px",
                    borderRadius: "8px",
                    minWidth: "150px"
                  }}
                >
                  <p className="m-1">🚚 Delivery Agent</p>
                  <hr />

                  <button
                    className="btn btn-danger btn-sm w-100"
                    onClick={logout}
                  >
                    Logout
                  </button>
                </div>
              )}

            </div>
          </>
        )}

      </div>

    </nav>
  );
}

export default Navbar;
