package com.ykv17.bookmyshow.service;

import com.ykv17.bookmyshow.exception.CityNotFoundException;
import com.ykv17.bookmyshow.models.Auditorium;
import com.ykv17.bookmyshow.models.City;
import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.Theatre;
import com.ykv17.bookmyshow.repository.AuditoriumRepository;
import com.ykv17.bookmyshow.repository.CityRepository;
import com.ykv17.bookmyshow.repository.EventRepository;
import com.ykv17.bookmyshow.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ShowService {
    private CityRepository cityRepository;
    private TheaterRepository theaterRepository;
    private AuditoriumRepository auditoriumRepository;
    private EventRepository eventRepository;

    @Autowired
    public ShowService(CityRepository cityRepository, TheaterRepository theaterRepository, EventRepository eventRepository, AuditoriumRepository auditoriumRepository) {
        this.cityRepository = cityRepository;
        this.theaterRepository = theaterRepository;
        this.eventRepository = eventRepository;
        this.auditoriumRepository = auditoriumRepository;
    }

    public List<Event> getEventsForCity(long cityId, long movieId) throws CityNotFoundException {
        /*Optional<City> cityOptional = cityRepository.findCityById(cityId);

        if (cityOptional.isEmpty()) {
            throw new CityNotFoundException("No City found with id: " + cityId);
        }

        City city = cityOptional.get();

        List<Theatre> theatres = theaterRepository.findAllByCity(city);*/

        List<Event> events = eventRepository.findAllByMovieId(movieId);

        List<Event> eventForSelectedMovies = new ArrayList<>();
        /*for(Event event: events){
            if(auditoriums.contains(event.getAuditorium())){
                eventForSelectedMovies.add(event);
            }
        }*/

        for(Event event: events){
            if(event.getAuditorium().getTheatre().getCity().getId() == cityId){
                eventForSelectedMovies.add(event);
            }
        }

        return eventForSelectedMovies;
    }
}
