import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("CUSTOMER");
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleRegister = (e) => {
    e.preventDefault();
    setLoading(true);

    API.post("/auth/register", { name, email, password, role })
      .then(() => {
        alert("Registered successfully ✅");
        navigate("/login");
      })
      .catch(() => alert("Registration failed ❌"))
      .finally(() => setLoading(false));
  };

  return (
    <div style={styles.bg}>
      <div style={styles.overlay}></div>

      <div style={styles.card}>
        <h2 style={styles.heading}>📝 Create Account</h2>

        <form onSubmit={handleRegister}>
          
          {/* NAME */}
          <div style={styles.inputGroup}>
            <span>👤</span>
            <input
              type="text"
              placeholder="Full Name"
              style={styles.input}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          {/* EMAIL */}
          <div style={styles.inputGroup}>
            <span>📧</span>
            <input
              type="email"
              placeholder="Email"
              style={styles.input}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          {/* PASSWORD */}
          <div style={styles.inputGroup}>
            <span>🔒</span>
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Password"
              style={styles.input}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
            <span
              style={styles.eye}
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? "🙈" : "👁"}
            </span>
          </div>

          {/* ROLE */}
          <div style={styles.inputGroup}>
            <span>🎯</span>
            <select
  style={styles.select}
  value={role}
  onChange={(e) => setRole(e.target.value)}
>
  <option value="CUSTOMER" style={{ color: "black" }}>
    Customer
  </option>
  <option value="DELIVERY" style={{ color: "black" }}>
    Delivery
  </option>
  <option value="ADMIN" style={{ color: "black" }}>
    Admin
  </option>
</select>
          </div>

          {/* BUTTON */}
          <button type="submit" style={styles.button}>
            {loading ? <div style={styles.spinner}></div> : "Register 🚀"}
          </button>
        </form>

        <p style={styles.footer}>
          Already have an account?{" "}
          <span style={styles.link} onClick={() => navigate("/login")}>
            Login
          </span>
        </p>
      </div>
    </div>
  );
}

export default Register;


const styles = {
  bg: {
    height: "100vh",
    backgroundImage:
      "url('https://images.unsplash.com/photo-1601924582975-7e74694e4586')",
    backgroundSize: "cover",
    backgroundPosition: "center",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  },

  overlay: {
    position: "absolute",
    width: "100%",
    height: "100%",
    background: "rgba(0,0,0,0.6)",
  },

  card: {
    position: "relative",
    zIndex: 2,
    width: "360px",
    padding: "30px",
    borderRadius: "15px",
    background: "rgba(255,255,255,0.1)",
    backdropFilter: "blur(12px)",
    boxShadow: "0 8px 32px rgba(0,0,0,0.3)",
    textAlign: "center",
    color: "white",
    animation: "floatCard 4s ease-in-out infinite",
  },

  heading: {
    marginBottom: "20px",
  },

  inputGroup: {
    display: "flex",
    alignItems: "center",
    background: "rgba(255,255,255,0.2)",
    padding: "10px",
    borderRadius: "10px",
    marginBottom: "15px",
  },

  input: {
    flex: 1,
    border: "none",
    outline: "none",
    background: "transparent",
    color: "white",
    marginLeft: "10px",
  },

  select: {
  flex: 1,
  border: "none",
  outline: "none",
  background: "rgba(255,255,255,0.2)",
  color: "white",
  marginLeft: "10px",
  padding: "5px",
  borderRadius: "5px"
},
  eye: {
    cursor: "pointer",
  },

  button: {
    width: "100%",
    padding: "12px",
    border: "none",
    borderRadius: "10px",
    background: "linear-gradient(135deg,#28a745,#20c997)",
    color: "white",
    fontWeight: "bold",
    marginTop: "10px",
    cursor: "pointer",
  },

  footer: {
    marginTop: "15px",
  },

  link: {
    color: "#ffcc00",
    cursor: "pointer",
    fontWeight: "bold",
  },

  spinner: {
    width: "20px",
    height: "20px",
    border: "3px solid white",
    borderTop: "3px solid transparent",
    borderRadius: "50%",
    animation: "spin 1s linear infinite",
    margin: "0 auto",
  },
};