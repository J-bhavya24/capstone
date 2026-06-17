import { useEffect, useState } from "react";
import API from "../services/api";
import AOS from "aos";
import "aos/dist/aos.css";

function PizzaList({ pizzasProp }) {

  const [pizzas, setPizzas] = useState([]);

  useEffect(() => {
    AOS.init();

    if (pizzasProp) {
      setPizzas(pizzasProp);
    } else {
      API.get("/pizzas")
        .then(res => setPizzas(res.data))
        .catch(err => console.log(err));
    }

  }, [pizzasProp]);

  // ✅ ✅ FIXED IMAGE FUNCTION (NO HTML ❌)
  const getImage = (name) => {

    if (!name) return "https://via.placeholder.com/300x200";

    const n = name.toLowerCase();

    if (n.includes("paneer")) return "paneer pizza.jpg";
    if (n.includes("chicken")) return "chicken pizza.jpg";
    if (n.includes("veg pizza")) return "veg pizza.png";
    if (n.includes("veg supreme")) return "Supremeveg.jpeg";
    if (n.includes("cheese")) return "cheese.gif";
    if (n.includes("corn")) return "corn pizza.jpeg";

    return "https://via.placeholder.com/300x200";
  };

  // ✅ ✅ FIXED ADD TO CART
  const addToCart = async (pizzaId) => {

    try {
      const userId = localStorage.getItem("userId");

      if (!userId) {
        alert("Please login first ❌");
        return;
      }

      await API.post("/cart", {
        userId: userId,
        pizzaId: pizzaId,
        quantity: 1
      });

      alert("Added to cart ✅");

    } catch (err) {
      console.error("Cart Error:", err);

      // ✅ ✅ HANDLE TOKEN EXPIRED
      if (err.response?.status === 403) {
        alert("Session expired. Login again 🔐");
        localStorage.clear();
        window.location.href = "/login";
      } else {
        alert("Failed to add ❌");
      }
    }
  };

  return (
    <div className="row">

      {pizzas.length > 0 ? (

        pizzas.map(p => (

          <div key={p.id} className="col-md-4 mb-4" data-aos="fade-up">

            <div className="card shadow-lg border-0">

              {/* ✅ IMAGE FIXED */}
              <img
                src={p.imageUrl || getImage(p.name)}
                alt={p.name}
                className="card-img-top"
                style={{ height: "200px", objectFit: "cover" }}
              />

              <div className="card-body text-center">

                <h4 className="fw-bold">{p.name}</h4>

                <p className="text-muted">{p.description}</p>

                <h5 className="text-success">₹{p.price}</h5>

                <button
                  className="btn btn-success mt-2"
                  onClick={() => addToCart(p.id)}
                >
                  Add to Cart 🛒
                </button>

              </div>

            </div>

          </div>

        ))

      ) : (
        <p className="text-center">No pizzas available ❌</p>
      )}

    </div>
  );
}

export default PizzaList;
