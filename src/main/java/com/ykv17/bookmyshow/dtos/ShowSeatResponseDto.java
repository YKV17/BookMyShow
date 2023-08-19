package com.ykv17.bookmyshow.dtos;

import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.models.ShowSeat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowSeatResponseDto {
    private List<ShowSeat> showSeats;
    private ResponseStatus responseStatus;
    private String message;
}
