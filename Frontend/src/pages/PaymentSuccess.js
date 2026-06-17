import { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";

function PaymentSuccess() {

  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const orderId = params.get("orderId");

    const timer = setTimeout(() => {
      navigate(`/delivery?orderId=${orderId}`); // ✅ Redirect to delivery
    }, 3000);

    return () => clearTimeout(timer);
  }, [navigate, location]);

  return (
    <div className="text-center mt-5">

      {/* ✅ SUCCESS MESSAGE */}
      <h1 className="text-success">🎉 Payment Successful</h1>

      <h3 className="mt-3">🍕 Your Order is Confirmed</h3>

      {/* ✅ LOADING ANIMATION */}
      <div className="mt-4">
        <div className="spinner-border text-success"></div>
      </div>

      <p className="mt-3">Redirecting to delivery tracking...</p>

    </div>
  );
}

export default PaymentSuccess;