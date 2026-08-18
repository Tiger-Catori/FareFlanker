// src/shared/components/ui/AppCard.jsx
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { searchFlights } from '../../lib/http/flightService';
import FormButton from './FormButton';
import FlightCard from '../flight/FlightCard';

const AppCard = () => {
  const navigate = useNavigate();

  // Form state
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const [date, setDate] = useState('');
  const [passengers, setPassengers] = useState(1);
  // Optional: add cabinClass and tripType if you add UI controls
  // For now we'll use defaults
  const cabinClass = 'ECONOMY';
  const tripType = 'ONE_WAY';

  // Results state
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      // The searchFlights function should send a POST with JSON body
      const data = await searchFlights({
        origin,
        destination,
        departureDate: date,
        cabinClass,
        passengers,
        tripType,
      });
      setFlights(data);
    } catch (err) {
      setError(err.message || 'Failed to fetch flights. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleCardClick = (flightId) => {
    // Navigate to details page, passing the search date as query param
    navigate(`/flight/${flightId}?date=${date}`);
  };

  return (
    <div className="card__container" id="search">
      <div className="card">
        <div className="search__box">
          <div className="heading__box">
            <h2 className="heading" data-aos="fade-right" data-aos-duration="1500">
              Search for Flights
            </h2>
          </div>

          <form onSubmit={handleSubmit}>
            <FormInputs
              origin={origin}
              setOrigin={setOrigin}
              destination={destination}
              setDestination={setDestination}
              date={date}
              setDate={setDate}
              passengers={passengers}
              setPassengers={setPassengers}
            />
            <FormButton type="submit" disabled={loading}>
              {loading ? 'Searching...' : 'Search Flights'}
            </FormButton>
          </form>

          {error && <div className="error-message">{error}</div>}

          <Results
            flights={flights}
            loading={loading}
            error={error}
            onCardClick={handleCardClick}
          />

        </div>
      </div>
    </div>
  );
};

export default AppCard;

// Results Component
const Results = ({ flights, loading, error, onCardClick }) => {
  return (
    <div className="results">
      {loading && <p>Loading flights...</p>}
      {!loading && flights.length === 0 && !error && (
        <p>No flights found. Try adjusting your search.</p>
      )}
      {!loading &&
        flights.map((flight) => (
          <FlightCard
            key={flight.id || flight.flightNumber}
            flight={flight}
            onClick={() => onCardClick(flight.id || flight.flightNumber)}
          />
        ))}
    </div>
  );
};

// FormInputs Component
const FormInputs = ({
  origin,
  setOrigin,
  destination,
  setDestination,
  date,
  setDate,
  passengers,
  setPassengers,
}) => {
  return (
    <>
      <div className="airport-search__box">
        <div className="input__box">
          <label htmlFor="from">From</label>
          <input
            type="text"
            className="airport__input"
            id="from"
            name="originEntry"
            placeholder="Enter origin (e.g., JFK)"
            value={origin}
            onChange={(e) => setOrigin(e.target.value.toUpperCase())}
            required
          />
        </div>

        <div className="input__box">
          <label htmlFor="to">To</label>
          <input
            type="text"
            className="airport__input"
            id="to"
            name="destinationEntry"
            placeholder="Enter destination (e.g., LAX)"
            value={destination}
            onChange={(e) => setDestination(e.target.value.toUpperCase())}
            required
          />
        </div>

        <div className="date-dropdown__row">
          <div className="date__box">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              className="date__input"
              id="date"
              name="date__entry"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              required
            />
          </div>

          <div className="dropdown">
            <label htmlFor="passengers">Passengers</label>
            <div className="select__wrapper">
              <select
                id="passengers"
                value={passengers}
                onChange={(e) => setPassengers(Number(e.target.value))}
              >
                {[1, 2, 3, 4, 5, 6, 7, 8].map((num) => (
                  <option key={num} value={num}>
                    {num}
                  </option>
                ))}
              </select>
              <ion-icon name="chevron-down-outline"></ion-icon>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
