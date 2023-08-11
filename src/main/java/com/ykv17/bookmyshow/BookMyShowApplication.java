package com.ykv17.bookmyshow;

import com.ykv17.bookmyshow.controller.UserController;
import com.ykv17.bookmyshow.dtos.UserLoginRequestDto;
import com.ykv17.bookmyshow.dtos.UserLoginResponseDto;
import com.ykv17.bookmyshow.enums.Pages;
import com.ykv17.bookmyshow.enums.ResponseStatus;
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

    private final Pages[] pages = Pages.values();
    Pages currentPage = Pages.MAIN;

    private Scanner scanner = new Scanner(System.in);

    private Stack<Pages> historyPages = new Stack<>();

    @Autowired
    public BookMyShowApplication(UserController userController) {
        this.userController = userController;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
        userSignUpRequestDto.setEmail("yogeshvrma007@gmail.com");
        userSignUpRequestDto.setPassword("p@$$word@123");
        userSignUpRequestDto.setName("Yogesh");
        userSignUpRequestDto.setPhoneNo("7891770668");
        userSignUpRequestDto.setAge(29);

        userController.signUpUser(userSignUpRequestDto);*/
        historyPages.push(Pages.MAIN);
        boolean canExist = false;

        UserLoginResponseDto userLoginResponseDto = null;

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
                        historyPages.push(Pages.LOGIN);
                    }

                    if (selectedOption == 3) {
                        historyPages.clear();
                    }

                }
                case LOGIN -> {
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
                        if(userLoginResponseDto.getMessage().equals("Invalid email or password")){
                            System.out.println(userLoginResponseDto.getMessage());
                        }else{
                            System.out.println(userLoginResponseDto.getMessage());
                            System.out.println("Please sign up first");
                            historyPages.pop();
                        }
                    }
                }
                case SIGNUP -> {
                }
                case CITIES -> {
                    if(userLoginResponseDto != null) {
                        System.out.println("------------------------------------SELECT CITY--------------------");
                        System.out.println("Welcome " + userLoginResponseDto.getName());

                        System.out.println("Please enter your city:");
                        String city = scanner.nextLine();
                    }else{
                        System.out.println("Something Went Wrong. Please try again");
                        historyPages.pop();
                    }
                }
                case MOVIES -> {
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
