import { Navigate } from "react-router-dom";

function PrivateRoute({ children, role }) {

  const userRole = localStorage.getItem("role");

  // ✅ If role is array (multiple roles)
  if (Array.isArray(role)) {
    if (!role.includes(userRole)) {
      return <Navigate to="/login" />;
    }
  }
  // ✅ Single role
  else {
    if (userRole !== role) {
      return <Navigate to="/login" />;
    }
  }

  return children;
}

export default PrivateRoute;
