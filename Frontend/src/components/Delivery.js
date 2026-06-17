import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import API from "../services/api";

function Delivery() {

  const [orderId, setOrderId] = useState("");
  const [delivery, setDelivery] = useState(null);

  const location = useLocation();

  // ✅ GET ORDER ID FROM URL
  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const id = params.get("orderId");

    if (id) {
      setOrderId(id);
      fetchDelivery(id);
    }
  }, [location]);

  // ✅ FETCH DELIVERY
  const fetchDelivery = (id) => {
    API.get(`/delivery/order/${id}`)
      .then((res) => {
        setDelivery(res.data);
      })
      .catch((err) => {
        console.error(err);
      });
  };

  // ✅ AUTO REFRESH
  useEffect(() => {
    if (!orderId) return;

    const interval = setInterval(() => {
      fetchDelivery(orderId);
    }, 3000);

    return () => clearInterval(interval);
  }, [orderId]);

  // ✅ PROGRESS %
  const getProgress = () => {
    if (!delivery) return 0;

    if (delivery.deliveryStatus === "PREPARING") return 33;
    if (delivery.deliveryStatus === "OUT_FOR_DELIVERY") return 66;
    if (delivery.deliveryStatus === "DELIVERED") return 100;

    return 0;
  };

  // ✅ TIMELINE STEP
  const getStep = () => {
    if (!delivery) return 0;

    if (delivery.deliveryStatus === "PREPARING") return 1;
    if (delivery.deliveryStatus === "OUT_FOR_DELIVERY") return 2;
    if (delivery.deliveryStatus === "DELIVERED") return 3;

    return 0;
  };

  return (
    <div className="container mt-4">

      <h2 className="text-center">🚚 Track Delivery</h2>

      {/* ✅ INPUT */}
      <div className="text-center mt-3">
        <input
          type="number"
          placeholder="Enter Order ID"
          className="form-control w-50 mx-auto"
          value={orderId}
          onChange={(e) => setOrderId(e.target.value)}
        />

        <button
          className="btn btn-primary mt-3"
          onClick={() => fetchDelivery(orderId)}
        >
          Track 🚀
        </button>
      </div>

      {/* ✅ DELIVERY CARD */}
      {delivery && (
        <div className="card mt-4 p-4 shadow text-center">

          <h4>Order ID: {delivery.orderId}</h4>

          <h5 className="mt-3">
            Status:
            <span
              className={
                delivery.deliveryStatus === "PREPARING"
                  ? "text-warning"
                  : delivery.deliveryStatus === "OUT_FOR_DELIVERY"
                  ? "text-primary"
                  : "text-success"
              }
            >
              {" "} {delivery.deliveryStatus}
            </span>
          </h5>

          <h5>ETA: {delivery.etaMinutes} mins ⏱</h5>

          {/* ✅ PROGRESS BAR */}
          <div className="progress mt-4">
            <div
              className="progress-bar progress-bar-striped progress-bar-animated"
              style={{ width: `${getProgress()}%` }}
            >
              {getProgress()}%
            </div>
          </div>

          {/* ✅ ✅ SWIGGY TIMELINE 🔥 */}
          <div className="mt-5">

            <div className="d-flex align-items-center justify-content-between">

              {/* Step 1 */}
              <div className="text-center">
                <div className={`rounded-circle p-3 ${getStep() >= 1 ? "bg-success text-white" : "bg-light"}`}>
                  🍳
                </div>
                <p>Preparing</p>
              </div>

              <div className="flex-grow-1 border-top mx-3"></div>

              {/* Step 2 */}
              <div className="text-center">
                <div className={`rounded-circle p-3 ${getStep() >= 2 ? "bg-success text-white" : "bg-light"}`}>
                  🚚
                </div>
                <p>Out for Delivery</p>
              </div>

              <div className="flex-grow-1 border-top mx-3"></div>

              {/* Step 3 */}
              <div className="text-center">
                <div className={`rounded-circle p-3 ${getStep() >= 3 ? "bg-success text-white" : "bg-light"}`}>
                  ✅
                </div>
                <p>Delivered</p>
              </div>

            </div>

          </div>

        </div>
      )}

    </div>
  );
}

export default Delivery;
