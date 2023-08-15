package com.ykv17.bookmyshow.dtos;

import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EventForCityResponseDto {
    private List<Event> events;
    private ResponseStatus responseStatus;
    private String message;
}
