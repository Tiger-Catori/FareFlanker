import NavbarComponent from "../shared/components/Navbar";
import TermsAndConditions from "../shared/components/TermsAndConditions";
import FooterComponent from "../shared/components/Footer";
import "../css/Navbar.css"
import "../css/Footer.css"

const TermsAndConditionsPage = () => {
  return (
    <>
      <NavbarComponent />
      <TermsAndConditions />
      <FooterComponent />
    </>
  );
};

export default TermsAndConditionsPage;
