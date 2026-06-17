import { useNavigate } from "react-router-dom";
import "./Welcome.css";

function Welcome() {
  const navigate = useNavigate();

  return (
    <div className="welcome-container">

      {/* LEFT IMAGE */}
       <div className="image-section">
  <img
    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7GaqLMeYxW_OpV6AGxUYfx9fJiEmL_7mPtmF7iB-Dd92XfhFghyVbDpw&s=10"
    alt="pizza"
  />
</div>


      {/* RIGHT CONTENT */}
      <div className="content-section">

        <h1 className="title">🍕 Welcome to Pizza Paradise</h1>

        <p className="funny">
          Hungry? 😋 Or just checking calories? 😂 <br />
          Don’t worry… we won’t tell anyone 🤫
        </p>

        <p className="tagline">
          ⚠️ One pizza = Never enough!
        </p>

        <div className="buttons">
          <button
            className="btn login-btn"
            onClick={() => navigate("/login")}
          >
            🔐 Login
          </button>

          <button
            className="btn register-btn"
            onClick={() => navigate("/register")}
          >
            📝 Register
          </button>
        </div>

      </div>
    </div>
  );
}

export default Welcome;