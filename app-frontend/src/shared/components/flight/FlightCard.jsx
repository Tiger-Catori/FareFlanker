import PropTypes from 'prop-types';
import "../../../css/FlightCard.css";

// Helper function: format ISO string or date to readable time (e.g., "Aug 17, 2026, 08:00 AM")
const formatDateTime = (dateInput) => {
  if (!dateInput) return '';
  const date = typeof dateInput === 'string' ? new Date(dateInput) : dateInput;
  if (isNaN(date)) return '';
  return date.toLocaleString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
    hour: "numeric",
    minute: "2-digit",
    hour12: true,
    timeZone: "UTC",
  })
}

const FlightCard = ({ flight, onClick }) => {
  const {
    airline,
    departureTime,
    arrivalTime,
    origin,
    destination,
    duration,
    stops,
    price,
    isDirect,
  } = flight

  // Format price display
  const formattedPrice = price ? `$${price}` : '';
  return (
    <>
      <div className='flight-card' onClick={onClick}>
        <Airline airline={airline} />
        <Times departureTime={departureTime} arrivalTime={arrivalTime} />
        <FlightRoute origin={origin} duration={duration} destination={destination} />
        <div className='stops__price'>
          <Stops isDirect={isDirect} stops={stops} />
          <Prices formattedPrice={formattedPrice} />
        </div>
      </div>
    </>
  )
};

FlightCard.propTypes = {
  flight: PropTypes.object.isRequired,
  onClick: PropTypes.func,
};

export default FlightCard;


const Airline = ({airline}) => {
  return (
    <>
      <div className='flight-card__airline'>{airline}</div>
    </>
  )
}

const Times = ({ departureTime, arrivalTime }) => {
  const formattedDeparture = formatDateTime(departureTime);
  const formattedArrival = formatDateTime(arrivalTime);
  return (
    <div className='flight-card__times'>
      <span>{formattedDeparture}</span> → <span>{formattedArrival}</span>
    </div>
  );
};


const FlightRoute = ({ origin, duration, destination }) => {
  return (
    <div className="flight-card__route">
      <span className="origin">{origin}</span>
      <span className="arrow">→</span>
      <span className="duration">{duration} mins</span>
      <span className="arrow">→</span>
      <span className="destination">{destination}</span>
    </div>
  );
};

const Stops = ({ isDirect, stops }) => {
  return (
    <>
      <div className="flight-card__stops">
        {isDirect ? 'Direct' : `${stops} stop${stops > 1 ? 's' : ''}`}
      </div>
    </>
  )
}

const Prices = ({ formattedPrice }) => {
  return (
    <>
      <div className="flight-card__price">
        {formattedPrice}
      </div>
    </>
  )
}
