import AppCard from "../shared/components/ui/AppCard"
import "../css/Button.css"
import "../css/Footer.css"
import NavbarComponent from "../shared/components/Navbar"
import HeroComponent from "../shared/components/Hero"
import FaqComponent from "../shared/components/Faqs"
import FooterComponent from "../shared/components/Footer"
import "../css/SearchForm.css"

const HomePage = () => {
  return (
    <div className="search-page">
      <NavbarComponent/>
      <HeroComponent />
      <FaqComponent/>
      <AppCard />
      <FooterComponent/>
    </div>
  );
};

export default HomePage;
