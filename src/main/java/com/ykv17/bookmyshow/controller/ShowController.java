package com.ykv17.bookmyshow.controller;

import com.ykv17.bookmyshow.dtos.EventForCityResponseDto;
import com.ykv17.bookmyshow.dtos.UserSignUpResponseDto;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.dtos.EventForCityRequestDto;
import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    public EventForCityResponseDto getEventsForCity(EventForCityRequestDto request) {
        EventForCityResponseDto response = new EventForCityResponseDto();
        try {
            List<Event> events = showService.getEventsForCity(request.getCityId(), request.getMovieId());
            response.setEvents(events);
            response.setResponseStatus(ResponseStatus.SUCCESS);
            response.setMessage("Success");
        } catch (Exception e) {
            response.setResponseStatus(ResponseStatus.ERROR);
            response.setMessage("Something went wrong");
        }
        return response;
    }

}
