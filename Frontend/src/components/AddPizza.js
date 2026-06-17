import { useState } from "react";
import API from "../services/api";

function AddPizza() {

  const [pizza, setPizza] = useState({
    name: "",
    category: "",
    description: "",
    price: ""
  });

  const handleChange = (e) => {
    setPizza({ ...pizza, [e.target.name]: e.target.value });
  };

  const addPizza = () => {
    API.post("/pizzas", pizza)
      .then(() => alert("Pizza added ✅"))
      .catch(() => alert("Only admin can add ❌"));
  };

  return (
    <div className="container mt-4">
      <h2>Add Pizza (Admin) 🍕</h2>

      <input name="name" className="form-control my-2" placeholder="Name" onChange={handleChange}/>
      <input name="category" className="form-control my-2" placeholder="Category" onChange={handleChange}/>
      <input name="description" className="form-control my-2" placeholder="Description" onChange={handleChange}/>
      <input name="price" className="form-control my-2" placeholder="Price" onChange={handleChange}/>

      <button className="btn btn-success" onClick={addPizza}>
        Add Pizza
      </button>
    </div>
  );
}

export default AddPizza;
