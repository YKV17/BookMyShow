package com.ykv17.bookmyshow.service;

import com.ykv17.bookmyshow.enums.CityMovieStatus;
import com.ykv17.bookmyshow.exception.CityNotFoundException;
import com.ykv17.bookmyshow.models.City;
import com.ykv17.bookmyshow.models.CityMovie;
import com.ykv17.bookmyshow.models.Movie;
import com.ykv17.bookmyshow.repository.CityMovieRepository;
import com.ykv17.bookmyshow.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private CityRepository cityRepository;
    private CityMovieRepository cityMovieRepository;


    @Autowired
    public MovieService(CityRepository cityRepository, CityMovieRepository cityMovieRepository) {
        this.cityRepository = cityRepository;
        this.cityMovieRepository = cityMovieRepository;
    }

    /*public City getCity(String name) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findCityByName(name);

        if(cityOptional.isEmpty()){
            throw new CityNotFoundException("No City found with the name " + name);
        }

        return cityOptional.get();
    }*/

    /*@Transactional*/
    public List<CityMovie> getPlayingMoviesForCity(String cityName, CityMovieStatus status) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.findCityByName(cityName);

        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException("No City found with the name " + cityName);
        }

        City city = cityOptional.get();

        List<CityMovie> cityMovies = cityMovieRepository.findAllByCityAndCityMovieStatus(city, status);


        return cityMovies;
    }
}
