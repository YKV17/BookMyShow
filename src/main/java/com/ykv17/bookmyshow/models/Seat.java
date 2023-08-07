package com.ykv17.bookmyshow.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends BaseModel{
    private String seatNumber;
    @Column(name = "rowz")
    private int row;
    @Column(name = "colz")
    private int col;
    @ManyToOne
    private SeatType seatType;
}
