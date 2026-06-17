import React from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Payment = ({ totalAmount }) => {
  const navigate = useNavigate();

  const handlePayment = async () => {

    try {
      const token = localStorage.getItem("token");
      const userId = localStorage.getItem("userId");

      // ✅ STEP 1: CREATE ORDER FIRST
      const orderRes = await axios.post(
        `http://localhost:8080/api/orders/place/${userId}`,
        {
          totalAmount: totalAmount
        },
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      console.log("✅ Order created:", orderRes.data);

      // ✅ STEP 2: OPEN RAZORPAY
      const options = {
        key: "rzp_test_T1vvhpY42GJMys",

        amount: Math.round(totalAmount * 100), // ✅ correct

        currency: "INR",
        name: "Pizza App",
        description: "Order Payment",

        handler: function (response) {
          console.log("✅ Payment success", response);

          alert("✅ Payment Successful!");

          // ✅ REDIRECT TO ORDERS
          navigate("/orders");
        },

        theme: {
          color: "#3399cc",
        },
      };

      const rzp = new window.Razorpay(options);
      rzp.open();

    } catch (error) {
      console.error("❌ Payment error:", error);
      alert("Something went wrong");
    }
  };

  return (
    <button className="btn btn-primary" onClick={handlePayment}>
      Pay ₹{totalAmount}
    </button>
  );
};

export default Payment;