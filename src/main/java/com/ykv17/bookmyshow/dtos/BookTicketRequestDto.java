package com.ykv17.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookTicketRequestDto {
    private List<Long> seatIds;
    private Long userId;
    private Long showId;
}
