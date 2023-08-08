package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.Seat;
import com.ykv17.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findAllBySeatInAndEvent(List<Seat> seats, Event event);

    ShowSeat save(ShowSeat showSeat);
}
