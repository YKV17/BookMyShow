package com.ykv17.bookmyshow.controller;

import com.ykv17.bookmyshow.dtos.BookTicketRequestDto;
import com.ykv17.bookmyshow.dtos.BookTicketResponseDto;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.exception.InvalidArgumentException;
import com.ykv17.bookmyshow.exception.SeatNotAvailableException;
import com.ykv17.bookmyshow.service.TicketService;
import jakarta.persistence.LockTimeoutException;
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
        BookTicketResponseDto bookTicketResponseDto = new BookTicketResponseDto();
        try {
            ticketService.bookTicket(request.getSeatIds(), request.getShowId(), request.getUserId());
        }catch (LockTimeoutException e){
            bookTicketResponseDto.setStatus(ResponseStatus.ERROR);
            bookTicketResponseDto.setMessage("Something went wrong");
        } catch (SeatNotAvailableException e) {
            bookTicketResponseDto.setStatus(ResponseStatus.ERROR);
            bookTicketResponseDto.setMessage("These seats are not available, try again with different seats");
        } catch (InvalidArgumentException e) {
            bookTicketResponseDto.setStatus(ResponseStatus.ERROR);
            bookTicketResponseDto.setMessage("Invalid User or Show");
        }
        return bookTicketResponseDto;
    }
}
