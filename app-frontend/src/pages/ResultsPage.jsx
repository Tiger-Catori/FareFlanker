import NavbarComponent from "../shared/components/Navbar";
import SearchResults from "../shared/components/SearchResults";
import FooterComponent from "../shared/components/Footer";
import "../css/Navbar.css";
import "../css/Footer.css";

const ResultPage = () => {
  return (
    <div className="result-page">
      <NavbarComponent />
      <SearchResults />
      <FooterComponent />
    </div>
  );
};

export default ResultPage;
