package az.edu.turing.service;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.entity.BookingEntity;
import az.edu.turing.model.BookingDto;

import java.util.Collection;
import java.util.function.Predicate;

public interface BookingService {

    void bookFlight(BookingDto bookingDto,int numberofseats);

    void cancelBooking(long bookingId);

    Collection<BookingEntity> getBookingsByPassenger(String fullName);
}
