import { useEffect, useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import { searchFlights } from "../lib/http/flightService";
import FlightCard from "./flight/FlightCard"; // adjust path if needed

const SearchResults = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const origin = searchParams.get('origin') || '';
  const destination = searchParams.get('destination') || '';
  const date = searchParams.get('date') || '';
  const passengers = parseInt(searchParams.get('passengers')) || 1;

  // Fixed for now – you can later add these to the search form
  const cabinClass = 'ECONOMY';
  const tripType = 'ONE_WAY';

  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchFlights = async () => {
      setLoading(true);
      try {
        const data = await searchFlights({
          originIata: origin,
          destinationIata: destination,
          departureDate: date,
          cabinClass,
          passengers,
          tripType,
        });
        setFlights(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    if (origin && destination && date) {
      fetchFlights();
    } else {
      navigate('/'); // redirect if params missing
    }
  }, [origin, destination, date, passengers, navigate]);

  const handleCardClick = (flightId) => {
    navigate(`/flight/${flightId}?date=${date}`);
  };

  if (loading) return <div className="loading">Searching flights...</div>;
  if (error) return <div className="error">Error: {error}</div>;

  return (
    <Results
      origin={origin}
      destination={destination}
      date={date}
      flights={flights}
      handleCardClick={handleCardClick}
    />
  );
};

export default SearchResults;

const Results = (
  {
    origin,
    destination,
    date,
    flights,
    handleCardClick,
  }
) => {
  return (
    <>
      <div className="search-results">
        <h1>{origin} → {destination}</h1>
        <p>{date}</p>
        {flights.length === 0 ? (
          <p>No flights found. Try adjusting your search.</p>
        ) : (
          <div className="results-list">
            {flights.map(flight => (
              <FlightCard
                key={flight.id || flight.flightNumber}
                flight={flight}
                onClick={() => handleCardClick(flight.id || flight.flightNumber)}
              />
            ))}
          </div>
        )}
      </div>
    </>
  )
}
