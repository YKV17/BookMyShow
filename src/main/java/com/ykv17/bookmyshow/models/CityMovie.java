package com.ykv17.bookmyshow.models;

import com.ykv17.bookmyshow.enums.CityMovieStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CityMovie extends BaseModel{
    @ManyToOne
    private City city;
    @ManyToOne
    private Movie movie;
    @Enumerated(EnumType.ORDINAL)
    private CityMovieStatus cityMovieStatus;
}
