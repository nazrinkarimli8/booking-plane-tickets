package az.edu.turing.service.impl;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.dao.FlightsDao;
import az.edu.turing.entity.BookingEntity;
import az.edu.turing.entity.FlightsEntity;
import az.edu.turing.exception.InvalidMenuActionException;
import az.edu.turing.model.BookingDto;
import az.edu.turing.service.BookingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;
    private final FlightsDao flightsDao;

    public BookingServiceImpl(BookingDao bookingDao, FlightsDao flightsDao) {
        this.bookingDao = bookingDao;
        this.flightsDao = flightsDao;
    }

    @Override
    public void bookFlight(BookingDto bookingDto, int numberofseats) {
        Collection<FlightsEntity> flights = new ArrayList<>(flightsDao.getAll());
        Collection<BookingEntity> bookings = new ArrayList<>(bookingDao.getAll());
        if (flights.stream().noneMatch(flightsEntity -> flightsEntity.getFlightId() == bookingDto.getFlightId())) {
            throw new InvalidMenuActionException("Flight not available");
        } else {
            int seats = flights.stream().filter(flightsEntity -> flightsEntity.getFlightId() == bookingDto.getFlightId()).findFirst().get().getSeats();
            if (numberofseats > seats) throw new InvalidMenuActionException("Number of seats exceeded");
            BookingEntity bookingEntity = new BookingEntity(bookingDto.getFlightId(), bookingDto.getPassengerNames());
            seats -= numberofseats;
            bookings.add(bookingEntity);
            bookingDao.save(bookings);
            flights.stream().filter(flightsEntity -> flightsEntity.getFlightId() == bookingDto.getFlightId()).findFirst().get().setSeats(seats);
            flightsDao.save(flights);
        }
    }

    @Override
    public void cancelBooking(long ticketId) {
        Collection<BookingEntity> bookings = new ArrayList<>(bookingDao.getAll());
        Collection<FlightsEntity> flights = new ArrayList<>(flightsDao.getAll());
        BookingEntity matchingBooking = bookings.stream().filter(booking -> booking.getTicketId() == ticketId).findFirst().orElse(null);

        if (matchingBooking != null) {

            Optional<FlightsEntity> optionalFlight = flights.stream().filter(flightsEntity -> flightsEntity.getFlightId() == matchingBooking.getFlightId()).findFirst();

            if (optionalFlight.isPresent()) {
                FlightsEntity flight = optionalFlight.get();

                int numberOfPassengers = matchingBooking.getPassengerNames().size();
                flight.setSeats(flight.getSeats() + numberOfPassengers);
                bookingDao.delete(ticketId);
                flightsDao.save(flights);
                System.out.println("Booking cancelled");
            } else {
                System.out.println("Flight not found for flight ID: " + matchingBooking.getFlightId());
            }
        } else {
            System.out.println("Booking not found for ticket ID: " + ticketId);
        }
    }


    @Override
    public Collection<BookingEntity> getBookingsByPassenger(String fullName) {
        Predicate<BookingEntity> predicate = booking -> booking.getPassengerNames().contains(fullName);

        return bookingDao.findAllBy(predicate);
    }
}
