package com.ykv17.bookmyshow.controller;

import com.ykv17.bookmyshow.dtos.MoviesForCityRequestDto;
import com.ykv17.bookmyshow.dtos.MoviesForCityResponseDto;
import com.ykv17.bookmyshow.dtos.ShowSeatRequestDto;
import com.ykv17.bookmyshow.dtos.ShowSeatResponseDto;
import com.ykv17.bookmyshow.enums.CityMovieStatus;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.exception.CityNotFoundException;
import com.ykv17.bookmyshow.exception.EventNotFoundException;
import com.ykv17.bookmyshow.models.CityMovie;
import com.ykv17.bookmyshow.models.ShowSeat;
import com.ykv17.bookmyshow.service.MovieService;
import com.ykv17.bookmyshow.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SeatController {

    private SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    public ShowSeatResponseDto getSeatsForShow(ShowSeatRequestDto ShowSeatResponseDto) {
        ShowSeatResponseDto response = new ShowSeatResponseDto();
        try {
            List<ShowSeat> showSeats = seatService.getShowSeatsForEvent(ShowSeatResponseDto.getEventId());
            response.setShowSeats(showSeats);
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Success");
        } catch (EventNotFoundException e) {
            response.setResponseStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }

        return response;
    }
}
