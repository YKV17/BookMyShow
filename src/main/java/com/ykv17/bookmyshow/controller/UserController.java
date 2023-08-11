package com.ykv17.bookmyshow.controller;

import com.ykv17.bookmyshow.dtos.UserLoginRequestDto;
import com.ykv17.bookmyshow.dtos.UserLoginResponseDto;
import com.ykv17.bookmyshow.dtos.UserSignUpRequestDto;
import com.ykv17.bookmyshow.dtos.UserSignUpResponseDto;
import com.ykv17.bookmyshow.enums.ResponseStatus;
import com.ykv17.bookmyshow.exception.InvalidCredentialsException;
import com.ykv17.bookmyshow.exception.UserNotFoundException;
import com.ykv17.bookmyshow.models.User;
import com.ykv17.bookmyshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserSignUpResponseDto signUpUser(UserSignUpRequestDto userSignUpRequestDto) {
        UserSignUpResponseDto userSignUpResponseDto = new UserSignUpResponseDto();
        try {
            User user = userService.signUpUser(userSignUpRequestDto.getEmail(),
                    userSignUpRequestDto.getPassword(),
                    userSignUpRequestDto.getName(),
                    userSignUpRequestDto.getPhoneNo(),
                    userSignUpRequestDto.getAge()
            );
            userSignUpResponseDto.setUserId(user.getId());
            userSignUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            userSignUpResponseDto.setMessage("Success");
        } catch (Exception e) {
            userSignUpResponseDto.setResponseStatus(ResponseStatus.ERROR);
            userSignUpResponseDto.setMessage("Something went wrong");
        }
        return userSignUpResponseDto;
    }

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        try {
            User user = userService.login(
                    userLoginRequestDto.getEmail(),
                    userLoginRequestDto.getPassword()
            );
            userLoginResponseDto.setUserId(user.getId());
            userLoginResponseDto.setName(user.getName());
            userLoginResponseDto.setEmail(user.getEmail());
            userLoginResponseDto.setPhoneNumber(user.getPhoneNumber());
            userLoginResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            userLoginResponseDto.setMessage("Success");
        } catch (UserNotFoundException e) {
            userLoginResponseDto.setResponseStatus(ResponseStatus.ERROR);
            userLoginResponseDto.setMessage(e.getMessage());
        } catch (InvalidCredentialsException e){
            userLoginResponseDto.setResponseStatus(ResponseStatus.ERROR);
            userLoginResponseDto.setMessage(e.getMessage());
        }
        return userLoginResponseDto;
    }

}
