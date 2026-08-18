import { useEffect, useState } from 'react';
import { useParams, useSearchParams } from 'react-router-dom';
import { getFlightDetails } from '../shared/lib/http/flightService';

const FlightDetailsPage = () => {
  const { id } = useParams();
  const [searchParams] = useSearchParams();
  const date = searchParams.get('date') || new Date().toISOString().slice(0,10);

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

  if (loading) return <p>Loading...</p>
  if (error) return <p>Error: {error}</p>
  if (!flight) return <p>Flight not found</p>

// Formatting duration from minutes
  const formatDuration = (minutes) => {
    const hours = Math.floor(minutes / 60)
    const mins = minutes % 60;
    return `${hours}h ${mins}m`;
  }

  // Formatting date and time
  const formatDate = (isoString) => {
    const d = new Date(isoString);
    return d.toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });
  };
  const formatTime = (isoString) => {
    const d = new Date(isoString);
    return d.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', hour12: false });
  };

  return (
    <>
      <div className='flight-details'>
        <h2>Flight Details</h2>

        <div className='details-card'>
          <Price flight={flight}/>
          <Airline flight={flight}/>
          <FlightInfo flight={flight}/>
        </div>
      </div>
    </>
  )

}
export default FlightDetailsPage;

// Breaking up details into componments

const Price = ({flight}) => {
  return (
    <>
      <div className='price'>
        <h4>Total Price</h4>
        <h4>{flight.currency}{flight.price} per passenger</h4>
      </div>
    </>
  )
}

const Airline = ({flight}) => {
  return (
    <>
      <div className="airline-info">
        <h3>{flight.airline}</h3>
        <div className="times">
          <div>
            <strong>{formatTime(flight.departureTime)}</strong> {flight.origin}
            <div>{formatDate(flight.departureDate)}</div>
          </div>
          <div>🛫 Travel time: {formatDuration(flight.durationMinutes)}</div>
          <div>
            <strong>{formatTime(flight.arrivalTime)}</strong> {flight.destination}
            <div>{formatDate(flight.arrivalDate)}</div>
          </div>
        </div>
        {flight.stops > 0 && (
          <div>🔄 Layover: {flight.stops} stop{flight.stops > 1 ? 's' : ''}</div>
        )}
      </div>
    </>
  )
}

// const Baggage = () => {
//   return (
//     <>
//       <div className='baggage'>
//         <h4>Baggage Information</h4>
//         <p>Cabin Baggage: {flight.baggage.cabin}</p>
//         <p>Checked Baggage: {flight.baggage.checked} - ${flight.baggage.checkedPrice}</p>
//       </div>
//     </>
//   )
// }

const FlightInfo = ({flight}) => {
  return (
    <>
      <div className="flight-info">
        <h4>Flight Information</h4>
        <div>Flight Number: {flight.flightNumber}</div>
        <div>Class: {flight.cabinClass}</div>
      </div>
    </>
  )
}
