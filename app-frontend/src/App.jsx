import { Routes, Route } from "react-router-dom"
import HomePage from "./pages/HomePage";
import "aos/dist/aos.css";
import AOS from "aos";

import { useEffect } from "react";


const App = () => {

  useEffect(() => {
    AOS.init({
      duration: 800,
      once: true,
      easing: "ease-out-quad",
    });
    AOS.refresh();
  }, []);
  return (
    <div id='home' className="container__body">
      <main className="main__content">
        <Routes>
          <Route path='/' element={<HomePage />}></Route>
        </Routes>
      </main>
    </div>
  )
}

export default App;
