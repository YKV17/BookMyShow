package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long aLong);
}
