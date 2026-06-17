import { useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    setLoading(true);

    API.post("/auth/login", { email, password })
      .then((res) => {
        console.log("✅ Login response:", res.data);

        // ✅ STORE CORRECT VALUES
        localStorage.setItem("token", res.data.token);
        localStorage.setItem("userId", res.data.userId);
        localStorage.setItem("role", res.data.role);

        // ✅ REDIRECT BASED ON ROLE
        if (res.data.role === "ADMIN") navigate("/admin");
        else if (res.data.role === "DELIVERY") navigate("/delivery-dashboard");
        else navigate("/home");
      })
      .catch(() => alert("Invalid credentials ❌"))
      .finally(() => setLoading(false));
  };

  return (
    <div style={styles.bg}>
      <div style={styles.overlay}></div>

      <div style={styles.card}>
        <h2 style={styles.heading}>🍕 Pizza Login</h2>

        <form onSubmit={handleLogin}>
          {/* EMAIL */}
          <div style={styles.inputGroup}>
            <span>📧</span>
            <input
              type="email"
              placeholder="Email address"
              style={styles.input}
              value={email}
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
              value={password}
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

          {/* BUTTON */}
          <button type="submit" style={styles.button}>
            {loading ? "Loading..." : "Login 🚀"}
          </button>
        </form>

        <p style={styles.footer}>
          No account?{" "}
          <span style={styles.link} onClick={() => navigate("/register")}>
            Register
          </span>
        </p>
      </div>
    </div>
  );
}

export default Login;

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
    width: "350px",
    padding: "30px",
    borderRadius: "15px",
    background: "rgba(255,255,255,0.1)",
    backdropFilter: "blur(12px)",
    color: "white",
    textAlign: "center",
  },
  heading: { marginBottom: "20px" },
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
  eye: { cursor: "pointer" },
  button: {
    width: "100%",
    padding: "12px",
    border: "none",
    borderRadius: "10px",
    background: "linear-gradient(135deg,#ff9800,#ff5722)",
    color: "white",
    fontWeight: "bold",
    marginTop: "10px",
    cursor: "pointer",
  },
  footer: { marginTop: "15px" },
  link: { color: "#ffcc00", cursor: "pointer" },
};