package com.ykv17.bookmyshow;

import com.ykv17.bookmyshow.controller.UserController;
import com.ykv17.bookmyshow.dtos.UserSignUpRequestDto;
import com.ykv17.bookmyshow.dtos.UserSignUpResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {

    private UserController userController;

    @Autowired
    public BookMyShowApplication(UserController userController) {
        this.userController = userController;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
        userSignUpRequestDto.setEmail("yogeshvrma007@gmail.com");
        userSignUpRequestDto.setPassword("p@$$word@123");
        userSignUpRequestDto.setName("Yogesh");
        userSignUpRequestDto.setPhoneNo("7891770668");
        userSignUpRequestDto.setAge(29);

        userController.signUpUser(userSignUpRequestDto);
    }
}
