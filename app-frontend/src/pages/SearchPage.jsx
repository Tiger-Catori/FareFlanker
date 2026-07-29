import AppCard from "../shared/components/ui/AppCard"
import "../css/Footer.css"
import FooterComponent from "../shared/components/Footer"
import "../css/SearchForm.css"
import "../css/button.css"

const SearchPage = () => {
  return (
    <div className="search-page">
      <AppCard />
      <FooterComponent/>
    </div>
  );
};

export default SearchPage;
