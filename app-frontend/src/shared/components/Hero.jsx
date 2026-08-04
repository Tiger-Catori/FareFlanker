import "../../css/Hero.css";
import AppButton from "./ui/AppButton";

const HeroComponent = () => {
  return <Hero/>
}
export default HeroComponent;

const Hero = () => {
  return (
    <section className="section__hero" id='hero'>
      <div className="hero__container">
        <HeroText/>
        <HeroImage/>
      </div>
    </section>
  )
}

const HeroText = () => {
  return (
    <div className="hero__text">
      <h2 className="hero__head">Stop overpaying for flights</h2>
      <p className="hero__p">Discover the best fares before you book.</p>
      <AppButton href="#search">Explore now!</AppButton>
    </div>
  )
}


const HeroImage = () => {
  return (
    <div className="hero__image">
      <div className="container__img">
        <img
          className="hero__img"
          src="src/assets/images/general/"
          alt=""
        />
      </div>
    </div>

  )
}
