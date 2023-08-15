package com.ykv17.bookmyshow.controller;

import com.ykv17.bookmyshow.dtos.MoviesForCityRequestDto;
import com.ykv17.bookmyshow.dtos.MoviesForCityResponseDto;
import com.ykv17.bookmyshow.enums.CityMovieStatus;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.exception.CityNotFoundException;
import com.ykv17.bookmyshow.models.CityMovie;
import com.ykv17.bookmyshow.models.Movie;
import com.ykv17.bookmyshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    public MoviesForCityResponseDto getMoviesForCity(MoviesForCityRequestDto moviesForCityRequestDto) {
        MoviesForCityResponseDto moviesForCityResponseDto = new MoviesForCityResponseDto();
        try {
            List<CityMovie> movies = movieService.getPlayingMoviesForCity(moviesForCityRequestDto.getCityName(), CityMovieStatus.PLAYING);
            moviesForCityResponseDto.setMovies(movies);
            moviesForCityResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            moviesForCityResponseDto.setMessage("Success");
        } catch (CityNotFoundException e) {
            moviesForCityResponseDto.setResponseStatus(ResponseStatus.ERROR);
            moviesForCityResponseDto.setMessage(e.getMessage());
        }

        return moviesForCityResponseDto;
    }
}
