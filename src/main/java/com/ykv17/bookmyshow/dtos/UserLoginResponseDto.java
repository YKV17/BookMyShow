package com.ykv17.bookmyshow.dtos;

import com.ykv17.bookmyshow.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {
    private Long userId;
    private String name;
    private String email;
    private String phoneNumber;
    private ResponseStatus responseStatus;
    private String message;
}
