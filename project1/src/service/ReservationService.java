package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private final Map<String, IRoom> rooms;
    private final Set<Reservation> reservations;


    private static final ReservationService instance = new ReservationService();


    private ReservationService() {
        this.rooms = new HashMap<>();
        this.reservations = new HashSet<>();
    }


    public static ReservationService getInstance() {
        return instance;
    }


    public void addRoom(IRoom room) {
        if (this.rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("RoomNumber has exist");
        } else {
            this.rooms.put(room.getRoomNumber(), room);
        }
    }

    public IRoom getARoom(String roomId) {
        return this.rooms.get(roomId);
    }

    public Reservation reserveRoom(Customer customer, IRoom room, Date checkIn, Date checkOut) {
        Reservation newReservation = new Reservation(customer, room, checkIn, checkOut);
        if (this.reservations.contains(newReservation)) {
            throw new IllegalArgumentException("This room is already reserved for these days");
        }
        this.reservations.add(newReservation);
        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Map<String, IRoom> availableRooms = new HashMap<>(this.rooms);

        for (Reservation reservation : this.reservations) {
            if (!isDateRangeAvailable(reservation, checkInDate, checkOutDate)) {
                availableRooms.remove(reservation.getRoom().getRoomNumber());
            }
        }

        return new ArrayList<>(availableRooms.values());
    }

    public boolean isDateRangeAvailable(Reservation reservation, Date checkIn, Date checkOut) {
        return checkOut.before(reservation.getCheckInDate()) || checkIn.after(reservation.getCheckOutDate());
    }
    public Collection<Reservation> getCustomersReservation(final Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : this.reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }
    public Collection<Reservation> getAllReservations() {
        return new ArrayList<>(this.reservations);
    }
}
