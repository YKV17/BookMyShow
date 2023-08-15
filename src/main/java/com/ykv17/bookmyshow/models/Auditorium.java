package com.ykv17.bookmyshow.models;

import com.ykv17.bookmyshow.enums.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Auditorium extends BaseModel{
    private String name;
    @OneToMany
    private List<Seat> seats;
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;
    @ManyToOne
    private Theatre theatre;

    @Override
    public boolean equals(Object obj) {
        return ((Auditorium)obj).getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
