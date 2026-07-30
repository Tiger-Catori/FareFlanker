import { Routes, Route } from "react-router-dom"
import HomePage from "./pages/HomePage";

const App = () => {
  return (
    <div className="container__body">
      <main className="main__content">
        <Routes>
          <Route path='/' element={<HomePage />}></Route>
        </Routes>
      </main>
    </div>
  )
}

export default App;
