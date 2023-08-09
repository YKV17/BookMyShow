package com.ykv17.bookmyshow.service;

import com.ykv17.bookmyshow.enums.ShowSeatStatus;
import com.ykv17.bookmyshow.enums.TicketStatus;
import com.ykv17.bookmyshow.exception.InvalidArgumentException;
import com.ykv17.bookmyshow.exception.SeatNotAvailableException;
import com.ykv17.bookmyshow.models.*;
import com.ykv17.bookmyshow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(SeatRepository seatRepository, ShowSeatRepository showSeatRepository, ShowRepository showRepository, UserRepository userRepository, TicketRepository ticketRepository) {
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }


    public Ticket bookTicket(List<Long> seatIds, Long showId, Long userId) throws SeatNotAvailableException, InvalidArgumentException {
        //1. for the seatIds get the corresponding showseats.
        //2. check the status of all the seats.
        //2.a. every seat is available.
        //2.a.1. Lock every seat by setting status to locked.
        //2.a.2. Create ticket object and return
        //2.b. Some of the seats are not available.
        //2.b.1. Throw an exception

        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);
        Optional<Event> eventOptional = showRepository.findById(showId);
        if (eventOptional.isEmpty()) {
            throw new InvalidArgumentException(
                    "Show by: " + showId + "doesn't exist."
            );
        }
        Event event = eventOptional.get();

        lockCheckAndUpdateShowSeatStatusIfAvailable(seats, event);

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new InvalidArgumentException("User with Id: " + userId + " doesn't exist");
        }

        Ticket ticket = new Ticket();
        ticket.setBookedBy(userOptional.get());
        ticket.setTicketStatus(TicketStatus.PROCESSING);
        ticket.setEvent(event);
        ticket.setSeats(seats);
        ticket.setAmount(0);
        ticket.setTimeOfBooking(new Date());

        Ticket savedTicket = ticketRepository.save(ticket);

        return savedTicket;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, timeout = 10)
    public void lockCheckAndUpdateShowSeatStatusIfAvailable(List<Seat> seats, Event event) throws SeatNotAvailableException {
        List<ShowSeat> showSeats = showSeatRepository.findAllBySeatInAndEvent(seats, event);

        for (ShowSeat showSeat : showSeats) {
            if (!(showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE) ||
                    showSeat.getShowSeatStatus().equals(ShowSeatStatus.LOCKED) && (new Date().getTime() - showSeat.getLockedAt().getTime()) > 15 * 60 * 1000L)) {
                throw new SeatNotAvailableException();
            }
        }

        List<ShowSeat> savedShowSeats = new ArrayList<>();
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.LOCKED);
            showSeat.setLockedAt(new Date());
            savedShowSeats.add(showSeatRepository.save(showSeat));
        }
    }
}
