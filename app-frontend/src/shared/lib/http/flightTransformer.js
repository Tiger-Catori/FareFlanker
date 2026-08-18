export const transformFlight = (dto) => ({
  id: dto.flightNumber,
  airline: dto.airlineName,
  flightNumber: dto.flightNumber,
  origin: dto.originIata,
  destination: dto.destinationIata,
  originCity: dto.originCity,
  destinationCity: dto.destinationCity,
  departureTime: dto.departureTime, // keep as ISO string
  arrivalTime: dto.arrivalTime,
  durationMinutes: dto.durationMinutes,
  stops: dto.stops,
  price: dto.price,
  currency: dto.currency,
  cabinClass: dto.cabinClass,
});
