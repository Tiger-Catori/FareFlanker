import { Routes, Route } from "react-router-dom"
import SearchPage from "./pages/SearchPage";

const App = () => {
  return (
    <div className="container__body">
      <main className="main__content">
        <Routes>
          <Route path='/' element={<SearchPage />}></Route>
        </Routes>
      </main>
    </div>
  )
}

export default App;
