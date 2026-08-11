import PropTypes from 'prop-types';

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
        <Stops isDirect={isDirect} stops={stops} />
        <Prices formattedPrice={formattedPrice} />
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

const Times = ({ arrivalTime, departureTime }) => {
  return (
    <>
      <div className='flight-card__times'>
        <span>{departureTime}</span> → <span>{arrivalTime}</span>
      </div>
    </>
  )
}


const FlightRoute = ({origin, duration, destination}) => {
  return (
    <>
      <div className='flight-card__route'>
                {origin} → {duration} → {destination}
      </div>
    </>
  )
}

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
