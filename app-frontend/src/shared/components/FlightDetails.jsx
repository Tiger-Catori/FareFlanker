// src/shared/components/FlightDetails.jsx

const Airline = ({ flight }) => {
  return (
    <div className="airline-info">
      <h3>{flight.airline}</h3>
      <div className="times">
        <div>
          <strong>{formatTime(flight.departureTime)}</strong> {flight.origin}
          <div>{formatDate(flight.departureTime)}</div>
        </div>
        <div>🛫 Travel time: {formatDuration(flight.durationMinutes)}</div>
        <div>
          <strong>{formatTime(flight.arrivalTime)}</strong> {flight.destination}
          <div>{formatDate(flight.arrivalTime)}</div>
        </div>
      </div>
      {flight.stops > 0 && (
        <div>🔄 Layover: {flight.stops} stop{flight.stops > 1 ? 's' : ''}</div>
      )}
    </div>
  );
};

const FlightInfo = ({ flight }) => {
  return (
    <div className="flight-info">
      <h4>Flight Information</h4>
      <div>Flight Number: {flight.flightNumber}</div>
      <div>Class: {flight.cabinClass}</div>
    </div>
  );
};

// Main component
const FlightDetails = ({ flight }) => {
  return (
    <div className="flight-details">
      <h2>Flight Details</h2>
      <div className="details-card">
        <Price flight={flight} />
        <Airline flight={flight} />
        <FlightInfo flight={flight} />
      </div>
    </div>
  );
};

export default FlightDetails;
