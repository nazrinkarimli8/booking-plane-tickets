package az.edu.turing.service.impl;

import az.edu.turing.dao.FlightsDao;
import az.edu.turing.entity.FlightsEntity;
import az.edu.turing.model.FlightsDto;
import az.edu.turing.service.FlightsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class FlightsServiceImpl implements FlightsService {

    private final FlightsDao flightsDao;

    public FlightsServiceImpl(FlightsDao flightsDao) {
        this.flightsDao = flightsDao;
    }

    @Override
    public void createFlights(FlightsDto flightsDto) {
        FlightsEntity flightsEntity = new FlightsEntity(
                flightsDto.getDepartureDateTime(),
                flightsDto.getDestination(),
                flightsDto.getSeats());
        Collection<FlightsEntity> flights = flightsDao.getAll();
        flights.add(flightsEntity);
        flightsDao.save(flights);
    }

    @Override
    public Collection<FlightsDto> getAllFlights() {
        Collection<FlightsEntity> flights = flightsDao.getAll();
        ArrayList<FlightsDto> flightsDto = new ArrayList<>();
        flights.stream().forEach(flightsEntity -> flightsDto.add(new FlightsDto(flightsEntity.getFlightId(), flightsEntity.getDepartureDateTime(), flightsEntity.getDestination(), flightsEntity.getLocation(), flightsEntity.getSeats())));
        return flightsDto;
    }

    @Override
    public List<FlightsDto> getAllFlightsByLocation(String location) {
        return getAllFlights().stream().filter(flightsDto -> flightsDto.getLocation().equalsIgnoreCase(location)).toList();
    }
    @Override
    public List<FlightsDto> getAllFlightsByDestination(String destination) {
        return getAllFlights().stream().filter(flightsDto -> flightsDto.getDestination().equalsIgnoreCase(destination)).toList();
    }
    @Override
    public List<FlightsDto> getAllFlightsByFlightId(long flightId) {
        return getAllFlights().stream().filter(flightsDto -> flightsDto.getFlightId() == flightId).toList();
    }

    @Override
    public Optional<FlightsDto> getOneFlightByFlightId(long flightId) {
        return getAllFlights().stream().filter(flightsDto -> flightsDto.getFlightId() == flightId).findFirst();
    }

    @Override
    public List<FlightsDto> flightsInNext24Hours(String location, LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);
        Collection<FlightsDto> allFlights = getAllFlights();
        return allFlights.stream()
                .filter(flightsDto -> flightsDto.getLocation().equalsIgnoreCase(location)
                        && flightsDto.getDepartureDateTime().isAfter(now)
                        && flightsDto.getDepartureDateTime().isBefore(next24Hours))
                .toList();
    }
    //    @Override
//    public void cancelFlight(int flightId) {
//        Collection<FlightsEntity> flights = flightsDao.getAll();
//        flights.removeIf(flightsEntity -> flightsEntity.getFlightId()==flightId);
//        flightsDao.save(flights);
//    }

    //    @Override
//    public List<FlightsDto> getAllFlightsBy(Predicate<FlightsDto> predicate) {
//        return getAllFlights().stream().filter(predicate).toList();
//    }

//    @Override
//    public Optional<FlightsDto> getOneFlightBy(Predicate<FlightsDto> predicate) {
//       return getAllFlights().stream().filter(predicate).findFirst();
//    }

}