import { useEffect, useState } from "react";
import API from "../services/api";
import toast from "react-hot-toast";
import { motion } from "framer-motion";
import { FaShoppingCart, FaRupeeSign, FaPizzaSlice } from "react-icons/fa";
import { Bar, Pie } from "react-chartjs-2";
import "chart.js/auto";
import "../App.css";
import "./Admin.css";

function AdminDashboard() {

  const [tab, setTab] = useState("dashboard");
  const [orders, setOrders] = useState([]);
  const [pizzas, setPizzas] = useState([]);
  const [loading, setLoading] = useState(true);

  const [newPizza, setNewPizza] = useState({
    name: "", category: "", description: "", price: ""
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
    setLoading(true);

    Promise.all([
      API.get("/admin/orders"),
      API.get("/pizzas")
    ])
      .then(([o, p]) => {
        setOrders(o.data);
        setPizzas(p.data);
      })
      .finally(() => setLoading(false));
  };

  // ✅ FIXED FIELD
  const revenue = orders.reduce(
    (sum, o) => sum + Number(o.totalAmount || 0),
    0
  );

  const addPizza = () => {
    API.post("/pizzas", { ...newPizza, available: true })
      .then(() => {
        toast.success("Pizza added ✅");
        fetchData();
      })
      .catch(() => toast.error("Error ❌"));
  };

  const deletePizza = (id) => {
    API.delete(`/pizzas/${id}`)
      .then(() => {
        toast.success("Deleted ✅");
        fetchData();
      })
      .catch(() => toast.error("Error ❌"));
  };

  if (loading) {
    return (
      <div className="loader">
        <div className="spinner"></div>
      </div>
    );
  }

  return (
    <div className="admin-container">

      {/* ✅ NAVBAR */}
      <div className="topbar">
        <h2>🍕 Pizza Admin</h2>

        <div className="tabs">
          <button onClick={()=>setTab("dashboard")} className={tab==="dashboard"?"active":""}>Dashboard</button>
          <button onClick={()=>setTab("orders")} className={tab==="orders"?"active":""}>Orders</button>
          <button onClick={()=>setTab("analytics")} className={tab==="analytics"?"active":""}>Analytics</button>
        </div>
      </div>

      {/* ✅ DASHBOARD */}
      {tab === "dashboard" && (
        <>
          <div className="cards">

            <motion.div className="card" whileHover={{scale:1.05}}>
              <FaShoppingCart className="icon" />
              <h4>Orders</h4>
              <p>{orders.length}</p>
            </motion.div>

            <motion.div className="card green" whileHover={{scale:1.05}}>
              <FaRupeeSign className="icon" />
              <h4>Revenue</h4>
              <p>₹{revenue}</p>
            </motion.div>

            <motion.div className="card blue" whileHover={{scale:1.05}}>
              <FaPizzaSlice className="icon" />
              <h4>Pizzas</h4>
              <p>{pizzas.length}</p>
            </motion.div>

          </div>

          {/* ADD */}
          <div className="box">
            <h3>Add Pizza</h3>

            <div className="form">
              <input placeholder="Name"
                value={newPizza.name}
                onChange={e => setNewPizza({...newPizza,name:e.target.value})}
              />
              <input placeholder="Category"
                onChange={e => setNewPizza({...newPizza,category:e.target.value})}
              />
              <input placeholder="Description"
                onChange={e => setNewPizza({...newPizza,description:e.target.value})}
              />
              <input type="number" placeholder="Price"
                onChange={e => setNewPizza({...newPizza,price:e.target.value})}
              />

              <button onClick={addPizza}>Add</button>
            </div>
          </div>

          {/* TABLE */}
          <div className="box">
            <h3>Pizza List</h3>

            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Category</th>
                  <th>Price</th>
                  <th>Rating</th>
                  <th>Action</th>
                </tr>
              </thead>

              <tbody>
                {pizzas.map(p => (
                  <tr key={p.id}>
                    <td>{p.name}</td>
                    <td>{p.category}</td>
                    <td>₹{p.price}</td>
                    <td>⭐ {Math.floor(Math.random()*2 + 4)}</td>
                    <td>
                      <button className="delete" onClick={()=>deletePizza(p.id)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </>
      )}

      {/* ✅ ORDERS */}
      {tab === "orders" && (
        <div className="box">
          <h3>Orders</h3>

          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Status</th>
                <th>Total</th>
              </tr>
            </thead>

            <tbody>
              {orders.map(o => (
                <tr key={o.id}>
                  <td>{o.id}</td>
                  <td className={`status ${o.status.toLowerCase()}`}>
                    {o.status}
                  </td>
                  <td>₹{o.totalAmount}</td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* TIMELINE */}
          <div className="timeline">
            <div className="step done">PLACED</div>
            <div className="step done">PREPARING</div>
            <div className="step">OUT</div>
            <div className="step">DELIVERED</div>
          </div>
        </div>
      )}

      {/* ✅ ANALYTICS (SMALL UI ✅) */}
      {tab === "analytics" && (
        <div className="analytics-container">

          <div className="mini-cards">
            <div className="mini-card">
              <h5>Orders</h5>
              <p>{orders.length}</p>
            </div>
            <div className="mini-card">
              <h5>Revenue</h5>
              <p>₹{revenue}</p>
            </div>
            <div className="mini-card">
              <h5>Pizzas</h5>
              <p>{pizzas.length}</p>
            </div>
          </div>

          <div className="small-charts">

            <div className="chart-box-small">
              <Bar data={{
                labels:["Orders","Revenue"],
                datasets:[{
                  data:[orders.length,revenue],
                  backgroundColor:["#ff4d4d","#22c55e"]
                }]
              }}/>
            </div>

            <div className="chart-box-small">
              <Pie data={{
                labels:pizzas.map(p=>p.name),
                datasets:[{
                  data:pizzas.map((p,i)=>(i+1)*2)
                }]
              }}/>
            </div>

          </div>

          <div className="small-table">
            <h4>Top Pizzas</h4>

            <table>
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Orders</th>
                </tr>
              </thead>

              <tbody>
                {pizzas.slice(0,5).map((p,i)=>(
                  <tr key={p.id}>
                    <td>{p.name}</td>
                    <td>{(i+1)*5}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

        </div>
      )}

    </div>
  );
}

export default AdminDashboard;