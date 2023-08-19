package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.Seat;
import com.ykv17.bookmyshow.models.ShowSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ShowSeat> findAllBySeatInAndEvent(List<Seat> seats, Event event);

    List<ShowSeat> findAllByEvent(Event event);

    ShowSeat save(ShowSeat showSeat);
}
