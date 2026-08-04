import NavbarComponent from "../shared/components/Navbar";
import FooterComponent from "../shared/components/Footer";
import NotFound from "../shared/components/NotFound";
import "../css/ErrorPage.css";

const ErrorPage = () => {
  return (
    <div className="error-page-wrapper">
      <NavbarComponent />
      <main className="not-found-main">
        <NotFound />
      </main>
      <FooterComponent />
    </div>
  );
};

export default ErrorPage;
