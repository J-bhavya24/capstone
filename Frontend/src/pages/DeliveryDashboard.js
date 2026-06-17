import { useEffect, useState } from "react";
import API from "../services/api";

function DeliveryDashboard() {

  const [deliveries, setDeliveries] = useState([]);

  useEffect(() => {
    loadDeliveries();
  }, []);

  // ✅ FETCH ALL DELIVERIES
  const loadDeliveries = () => {
    API.get("/delivery")
      .then(res => {
        console.log("Delivery Data:", res.data);
        setDeliveries(res.data);
      })
      .catch(err => console.log(err));
  };

  // ✅ UPDATE STATUS
  const updateStatus = (id, status) => {
    API.put(`/delivery/${id}?status=${status}`)
      .then(() => loadDeliveries())
      .catch(err => console.log(err));
  };

  // ✅ STATUS COLOR FUNCTION
  const getStatusStyle = (status) => {
    if (status === "PREPARING") return "bg-warning text-dark";
    if (status === "OUT_FOR_DELIVERY") return "bg-info text-white";
    if (status === "DELIVERED") return "bg-success text-white";
    return "";
  };

  return (
    <div className="container mt-4">

      <h2 className="text-center mb-4">🚚 Delivery Dashboard</h2>

      <div className="row">

        {deliveries.length === 0 && (
          <h5 className="text-center text-muted">
            No deliveries available ❗
          </h5>
        )}

        {deliveries.map(delivery => (

          <div key={delivery.id} className="col-md-4 mb-4">

            <div className="card p-3 shadow">

              {/* ✅ FIXED ORDER ID */}
              <h5>Order ID: <strong>{delivery.orderId}</strong></h5>

              <p>ETA: <strong>{delivery.etaMinutes} mins</strong></p>

              {/* ✅ STATUS BAR */}
              <div className={`p-2 text-center rounded ${getStatusStyle(delivery.deliveryStatus)}`}>
  <strong>
    {delivery.deliveryStatus === "PREPARING" && "🍳 Preparing"}
    {delivery.deliveryStatus === "OUT_FOR_DELIVERY" && "🚚 Out For Delivery"}
    {delivery.deliveryStatus === "DELIVERED" && "✅ Delivered"}
  </strong>
</div>

              {/* ✅ BUTTONS */}
              <div className="mt-3 text-center">

                <button
                  className="btn btn-warning btn-sm me-2"
                  onClick={() => updateStatus(delivery.id, "PREPARING")}
                >
                  Preparing
                </button>

                <button
                  className="btn btn-info btn-sm me-2"
                  onClick={() => updateStatus(delivery.id, "OUT_FOR_DELIVERY")}
                >
                  Out
                </button>

                <button
                  className="btn btn-success btn-sm"
                  onClick={() => updateStatus(delivery.id, "DELIVERED")}
                >
                  Done
                </button>

              </div>

            </div>

          </div>

        ))}

      </div>

    </div>
  );
}

export default DeliveryDashboard;