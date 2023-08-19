package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.models.Auditorium;
import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByMovieId(long movie);

    Optional<Event> findEventById(long eventId);

}
