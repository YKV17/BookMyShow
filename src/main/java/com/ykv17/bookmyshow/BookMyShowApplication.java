package com.ykv17.bookmyshow;

import com.ykv17.bookmyshow.controller.MovieController;
import com.ykv17.bookmyshow.controller.UserController;
import com.ykv17.bookmyshow.dtos.*;
import com.ykv17.bookmyshow.enums.Pages;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;
import java.util.Stack;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {

    private UserController userController;
    private MovieController movieController;

    private final Pages[] pages = Pages.values();
    Pages currentPage = Pages.MAIN;

    private Scanner scanner = new Scanner(System.in);

    private Stack<Pages> historyPages = new Stack<>();

    @Autowired
    public BookMyShowApplication(UserController userController, MovieController movieController) {
        this.userController = userController;
        this.movieController = movieController;
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
                                System.out.println((i + 1) + ". " + moviesForCityResponseDto.getMovies().get(i).getName());
                            }

                            System.out.println("Please select a movie for which you want to book tickets: (1 to " + moviesForCityResponseDto.getMovies().size() + ")");
                            int option = scanner.nextInt();
                            scanner.nextLine();
                        }
                    } else {
                        System.out.println("Something Went Wrong. Please try again");
                        historyPages.pop();
                    }
                }
                case THEATERS -> {
                }
                case SHOWS -> {
                }
                case SEATS -> {
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
