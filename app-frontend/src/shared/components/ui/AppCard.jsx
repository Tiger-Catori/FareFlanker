import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { searchFlights } from '../../lib/http/flightService';
import FormButton from './FormButton';
//import FlightCard from '../flight/FlightCard';

const AppCard = () => {
  const navigate = useNavigate();

  // Form state
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const [date, setDate] = useState('');
  const [passengers, setPassengers] = useState(1);

  // Results state
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    try {
      const data = await searchFlights(origin, destination, date, passengers);
      setFlights(data); // assuming backend returns an array of flight objects
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleCardClick = (flightId) => {
    navigate(`/flight/${flightId}`);
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

          <div className="results">
            {flights.map((flight) => (
              <FlightCard
                key={flight.id}
                flight={flight}
                onClick={() => handleCardClick(flight.id)}
              />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AppCard;

// The FormInputs component
const FormInputs = ({ origin, setOrigin, destination, setDestination, date, setDate, passengers, setPassengers }) => {
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
            placeholder="Enter origin"
            value={origin}
            onChange={(e) => setOrigin(e.target.value)}
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
            placeholder="Enter destination"
            value={destination}
            onChange={(e) => setDestination(e.target.value)}
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
                {[1, 2, 3, 4, 5, 6, 7, 8].map(num => (
                  <option key={num} value={num}>{num}</option>
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
