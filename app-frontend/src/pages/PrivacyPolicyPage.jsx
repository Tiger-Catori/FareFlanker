import NavbarComponent from "../shared/components/Navbar";
import PrivacyPolicy from "../shared/components/PrivacyPolicy";
import FooterComponent from "../shared/components/Footer";
import "../css/Navbar.css"
import "../css/Footer.css"

const PrivacyPolicyPage = () => {
  return (
    <>
      <NavbarComponent />
      <PrivacyPolicy />
      <FooterComponent />
    </>
  );
};

export default PrivacyPolicyPage;
