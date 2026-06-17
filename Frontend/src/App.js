import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Toaster } from "react-hot-toast";   // ✅ ADD THIS

import Navbar from "./components/Navbar";
import Login from "./components/Login";
import Cart from "./components/Cart";
import Orders from "./components/Orders";
import Register from "./components/Register";
import AddPizza from "./components/AddPizza";
import Delivery from "./components/Delivery";
import PaymentSuccess from "./pages/PaymentSuccess";

import Welcome from "./pages/Welcome";
import PrivateRoute from "./components/PrivateRoute";
import AdminDashboard from "./pages/AdminDashboard";
import DeliveryDashboard from "./pages/DeliveryDashboard";
import Home from "./pages/Home";
import ComboList from "./pages/ComboList";

function App() {
  return (
    <BrowserRouter>

      {/* ✅ TOAST (VERY IMPORTANT) */}
      <Toaster position="top-right" />

      <Navbar />

      <Routes>

        {/* ✅ DEFAULT */}
        <Route path="/" element={<Welcome />} />

        {/* ✅ LOGIN / REGISTER */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* ✅ CUSTOMER ROUTES */}
        <Route
          path="/home"
          element={
            <PrivateRoute role={["CUSTOMER", "ADMIN"]}>
              <Home />
            </PrivateRoute>
          }
        />

        <Route
          path="/cart"
          element={
            <PrivateRoute role="CUSTOMER">
              <Cart />
            </PrivateRoute>
          }
        />

        <Route
          path="/orders"
          element={
            <PrivateRoute role="CUSTOMER">
              <Orders />
            </PrivateRoute>
          }
        />

        <Route
          path="/delivery"
          element={
            <PrivateRoute role="CUSTOMER">
              <Delivery />
            </PrivateRoute>
          }
        />

        {/* ✅ ADMIN */}
        <Route
          path="/admin"
          element={
            <PrivateRoute role="ADMIN">
              <AdminDashboard />
            </PrivateRoute>
          }
        />

        <Route
          path="/add-pizza"
          element={
            <PrivateRoute role="ADMIN">
              <AddPizza />
            </PrivateRoute>
          }
        />

        {/* ✅ DELIVERY */}
        <Route
          path="/delivery-dashboard"
          element={
            <PrivateRoute role="DELIVERY">
              <DeliveryDashboard />
            </PrivateRoute>
          }
        />

        {/* ✅ SUCCESS */}
        <Route path="/success" element={<PaymentSuccess />} />

        {/* ✅ COMBOS */}
        <Route path="/combos" element={<ComboList />} />

        {/* ✅ FALLBACK */}
        <Route path="*" element={<Login />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;