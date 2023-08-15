package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.models.Auditorium;
import com.ykv17.bookmyshow.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    List<Auditorium> findAllByTheatreIn(List<Theatre> theatre);
}
