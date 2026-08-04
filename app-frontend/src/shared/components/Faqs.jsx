import "../../css/Faqs.css"
import { useState } from "react";

const data = [
  {
    id: 1,
    question: "What's the difference between 'one-way' and 'round-trip' search?",
    answer:
      "A one-way search finds flights from your origin to your destination on a single date, perfect for one-way travel. A round-trip search finds both outbound and return flights, showing you the total price for the entire journey. If you're planning a return trip, round-trip often gives you a better picture of the total cost.",
  },
  {
    id: 2,
    question: "Why do prices change when I click different dates on the calendar?",
    answer:
      "The price calendar shows the cheapest available fare for each departure date. Airline prices fluctuate based on demand, seasonality, and how far in advance you book. Clicking a different date runs a new search for that specific day, showing you the best options available for that travel date.",
  },
  {
    id: 3,
    question: "Why do I see different prices than what I see on other booking sites?",
    answer:
      "Our prices are sourced directly from our database of airline fares and are updated regularly. Prices can vary between platforms due to different data sources, caching, and the time of your search. We recommend using our prices as a comparison tool – always check with the airline directly for the final fare.",
  },
  {
    id: 4,
    question: "Can I book flights directly through this website?",
    answer:
      "Currently, this is a flight comparison and search platform. We help you find the best flights and prices, but we don't process bookings or payments. Once you find a flight you like, you can use the flight details to book directly with the airline. We're focused on making your search as easy and informative as possible.",
  },

];


const FaqComponent = () => {
  return (
    <FaqWrapper/>
  )
}
export default FaqComponent;

const FaqWrapper = () => {
  return (
    <div className="faq__wrapper" id='faqs'>
      <Accordian/>
    </div>
  )
}

const Accordian = () => {
  const [activeIndices, setActiveIndices] = useState([]);

  const toggleAccordian = (index) => {
    setActiveIndices((prevIndices) => {
      if (prevIndices.includes(index)) {
        // Remove index if it's already active
        return prevIndices.filter((i) => i != index)
      } else {
        // Adding index to active indices
        return [...prevIndices, index];
      }
    });
  };

  return (
    <div className="accordion">
      <FaqText />

      {data.map((item, index) => {
        const animation = index % 2 == 0;

        return (
          <FaqItem
              key={item.id}
              item={item}
              index={index}
              isActive={activeIndices.includes(index)}
              onClick={() => toggleAccordian(index)}
              data-aos={animation ? "fade-right" : "fade-left"}
              data-aos-duration="2000"
              data-aos-delay={index * 250}
              data-aos-easing="ease-in-out"
          />
        );
      })}
    </div>
  )
}

const FaqItem = ({ item, isActive, onClick, ...props }) => {
  return (
    <div className="faq-item" {...props}>
      <FaqQuestion
        question={item.question}
        isActive={isActive}
        onClick={onClick}
      />
      <FaqAnswer answer={item.answer} isActive={isActive} />
    </div>
  )
}

const FaqQuestion = ({ question, isActive, onClick }) => {
  return (
    <div
      className={`faq__question ${isActive ? "active" : ""}`}
      onClick={onClick}
    >
      <h3>{question}</h3>
      <span className="toggle-icon">{isActive ? "−" : "+"}</span>
    </div>
  );
};

const FaqAnswer = ({ answer, isActive }) => {
  return (
    <div className={`faq__answer ${isActive ? "open" : ""}`}>
      <p>{answer}</p>
    </div>
  );
};

const FaqText = () => {
  return (
    <div className="heading__box">
      <h2
        className="heading"
        data-aos="fade-right"
        data-aos-duration="1500"
      >
        Frequently asked questions
      </h2>
      {/* <p className="faq__text__description" id="faq__p">

      </p>*/}
    </div>
  );
};
