package com.ykv17.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    private String email;
    private String password;
}
