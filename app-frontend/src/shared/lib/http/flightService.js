import { apiFetch } from "./apiBase";
import { transformFlight } from "./flightTransformer";

/**
 * Search for flights using the backend's POST (/api/flights/search) endpoint
 * @param {Object} searchParams - { origin, destination, departureDate, cabinClass, passengers, tripType }
 * @returns {Promise<Array>} Transformed flight objects for the frontend.
 */
export const searchFlights = async (searchParams) => {
  const data = await apiFetch('/api/flights/search', {
    method: 'POST',
    body: JSON.stringify(searchParams)
  });
  // data is an array of FlightResultDTO from the backend.
  return data.map(transformFlight)
}

/**
 * Get detailed information for a specific flight on a specific date.
 * @param {string} flightId - The flight identifier (e.g., flightNumber or ID).
 * @param {string} date - The departure date in YYYY-MM-DD format.
 * @returns {Promise<Object>} Transformed flight detail object.
 */
export const getFlightDetails = async (flightId, date) => {
  const data = await apiFetch(`/api/flight/${flightId}?date=${date}`, {
    method: 'GET'
  });
  // data is a single FlightResultDTO object
  return transformFlight(data);
 }
