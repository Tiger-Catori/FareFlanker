import AppCard from "../shared/components/ui/AppCard"
import "../css/Button.css"
import "../css/Footer.css"
import HeroComponent from "../shared/components/Hero"
import FooterComponent from "../shared/components/Footer"
import "../css/SearchForm.css"

const HomePage = () => {
  return (
    <div className="search-page">
      <HeroComponent/>
      <AppCard />
      <FooterComponent/>
    </div>
  );
};

export default HomePage;
