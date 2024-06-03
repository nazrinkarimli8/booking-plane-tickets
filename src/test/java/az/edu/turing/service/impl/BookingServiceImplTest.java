//package az.edu.turing.service.impl;
//
//import az.edu.turing.dao.BookingDao;
//import az.edu.turing.entity.BookingEntity;
//import az.edu.turing.model.BookingDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Predicate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class BookingServiceImplTest {
//
//    private BookingDaoStub bookingDao;
//    private BookingServiceImpl bookingService;
//
//    @BeforeEach
//    void setUp() {
//        bookingDao = new BookingDaoStub();
//        bookingService = new BookingServiceImpl(bookingDao);
//    }
//
//    @Test
//    void bookFlight() {
//
//        BookingDto bookingDto = new BookingDto(1, 1, List.of("Alice", "Bob"));
//        BookingEntity bookingEntity = new BookingEntity( 1, List.of("Alice", "Bob"));
//
//        bookingService.bookFlight(bookingDto);
//
//        Collection<BookingEntity> bookings = bookingDao.getAll();
//        assertEquals(1, bookings.size());
//        assertEquals(bookingEntity, bookings.iterator().next());
//    }
//
//    @Test
//    void cancelBooking() {
//
//        long ticketId = 1;
//        bookingDao.save(new ArrayList<>(List.of(new BookingEntity( 1, List.of("Alice", "Bob")))));
//
//        bookingService.cancelBooking(ticketId);
//
//        Collection<BookingEntity> bookings = bookingDao.getAll();
//        assertEquals(0, bookings.size());
//    }
//
//    @Test
//    void getBookingsByPassenger() {
//
//        String fullName = "Alice";
//        bookingDao.save(new ArrayList<>(List.of(
//                new BookingEntity( 1, List.of("Alice", "Bob")),
//                new BookingEntity( 2, List.of("Charlie", "Alice"))
//        )));
//
//        Collection<BookingEntity> result = bookingService.getBookingsByPassenger(fullName);
//
//        assertEquals(2, result.size());
//    }
//
//    private static class BookingDaoStub extends BookingDao {
//        private Collection<BookingEntity> bookings = new ArrayList<>();
//
//        @Override
//        public boolean save(Collection<BookingEntity> bookings) {
//            this.bookings = bookings;
//            return true;
//        }
//
//        @Override
//        public Collection<BookingEntity> getAll() {
//            return new ArrayList<>(bookings);
//        }
//
//        @Override
//        public void delete(long ticketId) {
//            bookings.removeIf(booking -> booking.getTicketId() == ticketId);
//        }
//
//        @Override
//        public Optional<BookingEntity> findOneBy(Predicate<BookingEntity> predicate) {
//            return bookings.stream().filter(predicate).findFirst();
//        }
//
//        @Override
//        public Collection<BookingEntity> findAllBy(Predicate<BookingEntity> predicate) {
//            return bookings.stream().filter(predicate).toList();
//        }
//    }
//}
