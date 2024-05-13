package az.edu.turing.service.impl;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.entity.BookingEntity;
import az.edu.turing.entity.FlightsEntity;
import az.edu.turing.model.BookingDto;
import az.edu.turing.service.BookingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class BookingServiceImpl implements BookingService {

    private final BookingDao bookingDao;

    public BookingServiceImpl(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @Override
    public void bookFlight(BookingDto bookingDto) {
        BookingEntity bookingEntity = new BookingEntity(
                bookingDto.getFlightId(),
                bookingDto.getPassengerNames());
        Collection<BookingEntity> bookings = new ArrayList<>(bookingDao.getAll());
        bookings.add(bookingEntity);
        bookingDao.save(bookings);
    }

    @Override
    public void cancelBooking(long ticketId) {
        bookingDao.delete(ticketId);
    }

    @Override
    public Collection<BookingEntity> getBookingsByPassenger(String fullName) {
        Predicate<BookingEntity> predicate = booking ->
                booking.getPassengerNames().contains(fullName);

        return bookingDao.findAllBy(predicate);
    }
}
