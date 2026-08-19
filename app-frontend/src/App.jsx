import { Routes, Route } from "react-router-dom"
import HomePage from "./pages/HomePage";
import ResultPage from "./pages/ResultsPage";
import FlightDetailsPage from "./pages/FlightDetailsPage";
import ErrorPage from "./pages/ErrorPage"; // import error page
import PrivacyPolicyPage from "./pages/PrivacyPolicyPage"; // import privacy policy page
import TermsAndConditionsPage from "./pages/TermsAndConditionsPage"; // import terms and conditions page
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
          <Route path="/results" element={<ResultPage />} />
          <Route path="/flight/:id" element={<FlightDetailsPage />} />
          <Route path="/privacy-policy" element={<PrivacyPolicyPage />} />
          <Route
            path="/terms-and-conditions"
            element={<TermsAndConditionsPage />}
          />
          <Route path="/*" element={<ErrorPage />} />
        </Routes>
      </main>
    </div>
  )
}

export default App;
