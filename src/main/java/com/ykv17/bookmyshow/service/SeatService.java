package com.ykv17.bookmyshow.service;

import com.ykv17.bookmyshow.exception.CityNotFoundException;
import com.ykv17.bookmyshow.exception.EventNotFoundException;
import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.ShowSeat;
import com.ykv17.bookmyshow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private ShowSeatRepository showSeatRepository;
    private EventRepository eventRepository;

    @Autowired
    public SeatService(ShowSeatRepository showSeatRepository, EventRepository eventRepository) {
        this.showSeatRepository = showSeatRepository;
        this.eventRepository = eventRepository;
    }

    public List<ShowSeat> getShowSeatsForEvent(long eventId) throws EventNotFoundException {
        Optional<Event> eventOptional = eventRepository.findEventById(eventId);

        if(eventOptional.isEmpty()){
            throw new EventNotFoundException("No event found with Id: " + eventId);
        }

        Event event = eventOptional.get();

        List<ShowSeat> showSeats = showSeatRepository.findAllByEvent(event);

        return showSeats;
    }
}
