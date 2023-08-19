package com.ykv17.bookmyshow;

import com.ykv17.bookmyshow.controller.MovieController;
import com.ykv17.bookmyshow.controller.SeatController;
import com.ykv17.bookmyshow.controller.ShowController;
import com.ykv17.bookmyshow.controller.UserController;
import com.ykv17.bookmyshow.dtos.*;
import com.ykv17.bookmyshow.enums.Pages;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.models.Event;
import com.ykv17.bookmyshow.models.Movie;
import com.ykv17.bookmyshow.models.ShowSeat;
import com.ykv17.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.*;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {

    private UserController userController;
    private MovieController movieController;

    private ShowController showController;
    private SeatController seatController;

    Pages currentPage = Pages.MAIN;

    private Scanner scanner = new Scanner(System.in);

    private Stack<Pages> historyPages = new Stack<>();

    @Autowired
    public BookMyShowApplication(UserController userController,
                                 MovieController movieController,
                                 ShowController showController,
                                 SeatController seatController) {
        this.userController = userController;
        this.movieController = movieController;
        this.showController = showController;
        this.seatController = seatController;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        historyPages.push(Pages.MAIN);
        boolean canExist = false;

        UserLoginResponseDto userLoginResponseDto = null;
        MoviesForCityResponseDto moviesForCityResponseDto = null;
        EventForCityResponseDto eventForCityResponseDto = null;
        ShowSeatResponseDto showSeatResponseDto = null;
        Movie selectedMovie = null;
        long showId = -1;

        while (!canExist) {

            currentPage = (!historyPages.isEmpty()) ? historyPages.peek() : Pages.EXIT;

            switch (currentPage) {

                case MAIN -> {
                    System.out.println("-----------BookMyShow-----------");
                    System.out.println("Please select an option.");
                    System.out.println("1. Login");
                    System.out.println("2. SignUp");
                    System.out.println("3. Exit");

                    int selectedOption = scanner.nextInt();
                    scanner.nextLine();

                    if (selectedOption == 1) {
                        historyPages.push(Pages.LOGIN);
                    }

                    if (selectedOption == 2) {
                        historyPages.push(Pages.SIGNUP);
                    }

                    if (selectedOption == 3) {
                        historyPages.clear();
                    }

                }
                case LOGIN -> {
                    System.out.println("Do you want to go back? (y/n)");
                    String goBack = scanner.nextLine();
                    if (goBack.equalsIgnoreCase("y")) {
                        historyPages.pop();
                        continue;
                    }
                    System.out.println("-----------------------------LOGIN---------------------------");
                    System.out.println("Please enter your email id:");
                    String email = scanner.nextLine();

                    System.out.println("Please enter your password:");
                    String password = scanner.nextLine();

                    UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
                    userLoginRequestDto.setEmail(email);
                    userLoginRequestDto.setPassword(password);
                    userLoginResponseDto = userController.login(userLoginRequestDto);

                    if (userLoginResponseDto.getResponseStatus() == ResponseStatus.SUCCESS) {
                        historyPages.push(Pages.CITIES);
                    } else {
                        if (userLoginResponseDto.getMessage().equals("Invalid email or password")) {
                            System.out.println(userLoginResponseDto.getMessage());
                        } else {
                            System.out.println(userLoginResponseDto.getMessage());
                            System.out.println("Please sign up first");
                            historyPages.pop();
                        }
                    }
                }
                case SIGNUP -> {
                    System.out.println("Do you want to go back? (y/n)");
                    String goBack = scanner.nextLine();
                    if (goBack.equalsIgnoreCase("y")) {
                        historyPages.pop();
                        continue;
                    }
                    System.out.println("------------------------------------Sign Up-----------------------------");

                    System.out.println("Please enter your name:");
                    String name = scanner.nextLine();
                    System.out.println("Please enter your email id:");
                    String email = scanner.nextLine();
                    System.out.println("Please enter your password:");
                    String password = scanner.nextLine();
                    System.out.println("Please enter your phone No:");
                    String phoneNo = scanner.nextLine();
                    System.out.println("Please enter your age:");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
                    userSignUpRequestDto.setEmail(email);
                    userSignUpRequestDto.setPassword(password);
                    userSignUpRequestDto.setName(name);
                    userSignUpRequestDto.setPhoneNo(phoneNo);
                    userSignUpRequestDto.setAge(age);

                    UserSignUpResponseDto userSignUpResponseDto = userController.signUpUser(userSignUpRequestDto);

                    if (userSignUpResponseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)) {
                        System.out.println("User successfully registered.");
                        historyPages.pop();
                    } else {
                        System.out.println(userSignUpResponseDto.getMessage());
                    }
                }
                case CITIES -> {
                    System.out.println("Do you want to go back? (y/n)");
                    String goBack = scanner.nextLine();
                    if (goBack.equalsIgnoreCase("y")) {
                        historyPages.pop();
                        continue;
                    }
                    if (userLoginResponseDto != null) {
                        System.out.println("------------------------------------SELECT CITY-----------------------------");
                        System.out.println("Welcome " + userLoginResponseDto.getName());

                        System.out.println("Please enter your city:");
                        String city = scanner.nextLine();

                        if (city == null || city.isEmpty()) {
                            continue;
                        }

                        MoviesForCityRequestDto moviesForCityRequestDto = new MoviesForCityRequestDto();
                        moviesForCityRequestDto.setCityName(city);

                        moviesForCityResponseDto = movieController.getMoviesForCity(moviesForCityRequestDto);

                        if (moviesForCityResponseDto.getResponseStatus() == ResponseStatus.SUCCESS) {
                            historyPages.push(Pages.MOVIES);
                        } else {
                            System.out.println(moviesForCityResponseDto.getMessage());
                        }

                    } else {
                        System.out.println("Something Went Wrong. Please try again");
                        historyPages.pop();
                    }
                }
                case MOVIES -> {
                    System.out.println("Do you want to go back? (y/n)");
                    String goBack = scanner.nextLine();
                    if (goBack.equalsIgnoreCase("y")) {
                        historyPages.pop();
                        continue;
                    }

                    System.out.println("------------------------------------SELECT MOVIE-----------------------------");
                    if (moviesForCityResponseDto != null) {
                        if (moviesForCityResponseDto.getMovies() == null || moviesForCityResponseDto.getMovies().isEmpty()) {
                            System.out.println("Currently No Movie is playing in your city");
                            historyPages.pop();
                        } else {
                            System.out.println("Movies playing in your city:");
                            for (int i = 0; i < moviesForCityResponseDto.getMovies().size(); ++i) {
                                System.out.println((i + 1) + ". " + moviesForCityResponseDto.getMovies().get(i).getMovie().getName());
                            }

                            System.out.println("Please select a movie for which you want to book tickets: (1 to " + moviesForCityResponseDto.getMovies().size() + ")");
                            int selectedOption = scanner.nextInt();
                            selectedMovie = moviesForCityResponseDto.getMovies().get(selectedOption - 1).getMovie();
                            scanner.nextLine();
                            historyPages.push(Pages.SHOWS);
                        }
                    } else {
                        System.out.println("Something Went Wrong. Please try again");
                        historyPages.pop();
                    }
                }
                /*case THEATERS -> {


                }*/
                case SHOWS -> {
                    System.out.println("Do you want to go back? (y/n)");
                    String goBack = scanner.nextLine();
                    if (goBack.equalsIgnoreCase("y")) {
                        historyPages.pop();
                        continue;
                    }

                    System.out.println("------------------------------------SELECT SHOWS-----------------------------");
                    if (selectedMovie != null && moviesForCityResponseDto != null) {
                        EventForCityRequestDto request = new EventForCityRequestDto();
                        request.setCityId(moviesForCityResponseDto.getMovies().get(0).getCity().getId());
                        request.setMovieId(selectedMovie.getId());
                        eventForCityResponseDto = showController.getEventsForCity(request);

                        if (eventForCityResponseDto.getResponseStatus() == ResponseStatus.SUCCESS) {

                            Map<String, List<Event>> theaterEventMap = new HashMap<>();
                            for (Event event : eventForCityResponseDto.getEvents()) {
                                List<Event> addedEvents = theaterEventMap.getOrDefault(event.getAuditorium().getTheatre().getAddress(), new ArrayList<>());
                                addedEvents.add(event);
                                theaterEventMap.put(event.getAuditorium().getTheatre().getAddress(), addedEvents);
                            }

                            for (String key : theaterEventMap.keySet()) {
                                System.out.println(key + ": ");

                                for (Event event : theaterEventMap.get(key)) {
                                    System.out.println("\t" + event.getId() + ": (" + event.getLanguage() + ") " + event.getStartTime() + " - " + event.getEndTime());
                                }
                            }

                            System.out.println("Enter the id of the show for which you want to book tickets:");
                            showId = scanner.nextInt();
                            scanner.nextLine();


                            historyPages.push(Pages.SEATS);
                        } else {
                            System.out.println(eventForCityResponseDto.getMessage());
                        }
                    } else {
                        System.out.println("Something Went Wrong. Please try again");
                        historyPages.pop();
                    }

                }
                case SEATS -> {
                    System.out.println("Do you want to go back? (y/n)");
                    String goBack = scanner.nextLine();
                    if (goBack.equalsIgnoreCase("y")) {
                        historyPages.pop();
                        continue;
                    }

                    if (showId != -1) {

                        ShowSeatRequestDto showSeatRequestDto = new ShowSeatRequestDto();
                        showSeatRequestDto.setEventId(showId);
                        showSeatResponseDto = seatController.getSeatsForShow(showSeatRequestDto);

                        if (showSeatResponseDto.getResponseStatus() == ResponseStatus.SUCCESS) {
                            int r = 0;
                            int c = 0;
                            ShowSeat[][] showSeats = new ShowSeat[5][5];

                            for (int i = 0; i < showSeatResponseDto.getShowSeats().size(); i++) {
                                showSeats[r][c] = showSeatResponseDto.getShowSeats().get(i);

                                c++;
                                c = c % 5;

                                if(c == 0){
                                    r++;
                                }
                            }

                            for(int i = 0; i < showSeats.length; i++){
                                for(int j = 0; j < showSeats.length; j++){
                                    ShowSeat seat = showSeats[i][j];
                                    switch (seat.getShowSeatStatus()){

                                        case AVAILABLE -> {
                                            System.out.print("[" + seat.getSeat().getSeatType().getName().charAt(0) + ": " + " " + "]");
                                        }
                                        case BOOKED -> {
                                            System.out.print("[" + seat.getSeat().getSeatType().getName().charAt(0) + ": " + "X" + "]");
                                        }
                                        case LOCKED -> {
                                            System.out.print(" " + " " + "  " + " " + " ");
                                        }
                                    }

                                }
                                System.out.println("");
                                for(int j = 0; j < showSeats.length; j++){
                                    ShowSeat seat = showSeats[i][j];
                                    switch (seat.getShowSeatStatus()){

                                        case AVAILABLE -> {
                                            System.out.print(" " + seat.getSeat().getSeatNumber() + "  " + " " + " ");
                                        }
                                        case BOOKED -> {
                                            System.out.print(" " + seat.getSeat().getSeatNumber() + "  " + " " + " ");
                                        }
                                        case LOCKED -> {
                                            System.out.print(" " + " " + "  " + " " + " ");
                                        }
                                    }

                                }
                                System.out.println("\n");
                            }

                            System.out.println("\n");
                            System.out.println("-------------------SCREEN-------------------");

                            System.out.println("V: VIP");
                            System.out.println("P: PREMIUM");
                            System.out.println("E: EXECUTIVE");
                            System.out.println("N: NORMAL");
                        } else {
                            System.out.println("Something Went Wrong. Please try again");
                            historyPages.pop();
                        }

                    } else {
                        historyPages.pop();
                    }
                }
                case BOOKING -> {
                }
                case PAYMENTS -> {
                }
                default -> {
                    canExist = true;
                }
            }
        }


        /*
        Ask user if they want to Sign Up or Login
         1. Sign Up
           1.a. Ask User Email, Password, Name, Phone Number and Register.
         2. Login

           If User is not Admin.
           2.a. Ask User Email and Password and Login. (Return the User Object)
           2.b. Show User some options like Book Ticket, Show List of Movies(Out of Scope).

           2.b.1 Ask User their city.
           2.b.2 Show user list of all the movies currently showing in their city.
           2.b.3 Ask User to select a movie.
           2.b.4 Show user the list of theaters.
           2.b.5 Ask User to select the theater.
           2.b.6 Show the user list of shows and timings.
           2.b.7 Ask users to select a show.
           2.b.8 Show user al the seats in a 2d matrix with empty as available and X not available.
           2.b.9 Ask user to enter no of seats they want to book max 10 seats.
           2.b.10 Ask user the row and col for each seat one by one.
           2.b.11 Show total amount of the seats if available else show message to user that some seats might not available and try again.
           2.b.12 show users the current status of the show seats.


        * */
    }

}
