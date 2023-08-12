package com.ykv17.bookmyshow.repository;

import com.ykv17.bookmyshow.enums.CityMovieStatus;
import com.ykv17.bookmyshow.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityMovieRepository extends JpaRepository<CityMovie, Long> {

    List<CityMovie> findAllByCityAndCityMovieStatus(City city, CityMovieStatus cityMovieStatus);

}
