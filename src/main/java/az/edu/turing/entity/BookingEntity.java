package az.edu.turing.entity;

import java.util.List;
import java.util.Objects;

public final class BookingEntity {

    private static long MAX_ID = 0;
    private long ticketId;
    private long flightId;
    private List<String> passengerNames;

    public BookingEntity() {
    }

    public BookingEntity( long flightId, List<String> passengerNames) {
        this.ticketId = ++MAX_ID;
        this.flightId = flightId;
        this.passengerNames = passengerNames;

    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getFlightId() {
        return flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public List<String> getPassengerNames() {
        return passengerNames;
    }

    public void setPassengerNames(List<String> passengerNames) {
        this.passengerNames = passengerNames;
    }

    @Override
    public String toString() {
        return "{ticketId=%d, flightId=%d, passengerNames=%s}".formatted(ticketId, flightId, passengerNames);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingEntity that = (BookingEntity) o;
        return ticketId == that.ticketId && flightId == that.flightId && Objects.equals(passengerNames, that.passengerNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, flightId, passengerNames);
    }
}