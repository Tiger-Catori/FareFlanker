import { useEffect, useState } from 'react';
import { useParams, useSearchParams } from 'react-router-dom';
import { getFlightDetails } from '../shared/lib/http/flightService';
import FlightDetails from '../shared/components/FlightDetails'; // import the new component

const FlightDetailsPage = () => {
  const { id } = useParams();
  const [searchParams] = useSearchParams();
  const date = searchParams.get('date') || new Date().toISOString().slice(0, 10);

  const [flight, setFlight] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDetails = async () => {
      try {
        const data = await getFlightDetails(id, date);
        setFlight(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchDetails();
  }, [id, date]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
  if (!flight) return <p>Flight not found</p>;

  // Rendering the details component with the flight data
  return <FlightDetails flight={flight} />;
};

export default FlightDetailsPage;
