import { apiFetch } from "./apiBase";

export const searchFlights = (origin, destination, date, passengers) => {
  const params = new URLSearchParams({
    origin,
    destination,
    date,
    passengers: passengers || 1,
  });
  return apiFetch(`/api/flights/search?${params.toString()}`);
};

export const getFlightDetails = (flightId) => {
  return apiFetch(`/api/flights/${flightId}`);
};
