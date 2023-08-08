package com.ykv17.bookmyshow.controller;

import com.ykv17.bookmyshow.dtos.BookTicketRequestDto;
import com.ykv17.bookmyshow.dtos.BookTicketResponseDto;
import com.ykv17.bookmyshow.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public BookTicketResponseDto bookTicket(BookTicketRequestDto request){

    }
}
