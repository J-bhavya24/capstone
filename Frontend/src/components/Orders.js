import React, { useEffect, useState } from "react";
import axios from "axios";

function Orders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const userId = localStorage.getItem("userId");
      const token = localStorage.getItem("token");

      const res = await axios.get(
        `http://localhost:8080/api/orders/my/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setOrders(res.data);

    } catch (error) {
      console.error(error);
    }
  };

  // ✅ DOWNLOAD INVOICE
  const downloadInvoice = async (orderId) => {
    const token = localStorage.getItem("token");

    const res = await fetch(
      `http://localhost:8080/api/invoice/${orderId}`,
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    const blob = await res.blob();
    const url = window.URL.createObjectURL(blob);
    window.open(url);
  };

  // ✅ STATUS STYLE
  const getStatusStyle = (status) => {
    switch (status) {
      case "DELIVERED":
        return "text-success";
      case "OUT_FOR_DELIVERY":
        return "text-warning";
      case "PREPARING":
        return "text-info";
      default:
        return "text-secondary";
    }
  };

  return (
    <div className="container mt-4">
      <h2 className="text-center mb-4">📦 My Orders</h2>

      {orders.length === 0 ? (
        <h5 className="text-center text-muted">
          No orders found
        </h5>
      ) : (
        orders.map((order) => (
          <div
            key={order.id}
            className="card shadow-lg mb-4 p-4"
            style={{ borderRadius: "15px" }}
          >
            {/* ✅ HEADER */}
            <div className="d-flex justify-content-between">
              <h5>Order #{order.id}</h5>
              <span className={getStatusStyle(order.status)}>
                ● {order.status}
              </span>
            </div>

            {/* ✅ TOTAL */}
            <h3 className="mt-2">
              ₹{parseFloat(order.totalAmount).toFixed(2)}
            </h3>

            {/* ✅ ITEMS */}
            <div className="mt-3">
              {order.orderItems?.map((item, idx) => (
                <div key={idx}>
                  🍕 {item.pizza?.name} × {item.quantity}
                </div>
              ))}
            </div>

            {/* ✅ DELIVERY PROGRESS */}
            <div className="mt-3">
              <div className="progress">
                <div
                  className="progress-bar"
                  style={{
                    width:
                      order.status === "PLACED"
                        ? "25%"
                        : order.status === "PREPARING"
                        ? "50%"
                        : order.status === "OUT_FOR_DELIVERY"
                        ? "75%"
                        : "100%",
                  }}
                />
              </div>

              <small>
                {order.status === "PLACED" && "Order placed"}
                {order.status === "PREPARING" && "Preparing your food"}
                {order.status === "OUT_FOR_DELIVERY" &&
                  "On the way 🚚"}
                {order.status === "DELIVERED" &&
                  "Delivered ✅"}
              </small>
            </div>

            {/* ✅ DELIVERY STATUS */}
            {order.delivery && (
              <p className="mt-2">
                🚚 Delivery: {order.delivery.deliveryStatus}
              </p>
            )}

            {/* ✅ PAYMENT STATUS */}
            {order.payment && (
              <p>
                💳 Payment: {order.payment.paymentStatus}
              </p>
            )}

            {/* ✅ ACTIONS */}
            <div className="mt-3 d-flex gap-2">
              <button
                className="btn btn-dark"
                onClick={() => downloadInvoice(order.id)}
              >
                📄 Invoice
              </button>

              <button className="btn btn-outline-primary">
                🔁 Reorder
              </button>
            </div>
          </div>
        ))
      )}
    </div>
  );
}

export default Orders;