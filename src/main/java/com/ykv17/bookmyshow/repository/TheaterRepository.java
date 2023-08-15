package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.models.City;
import com.ykv17.bookmyshow.models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theatre, Long> {

    List<Theatre> findAllByCity(City city);

}
