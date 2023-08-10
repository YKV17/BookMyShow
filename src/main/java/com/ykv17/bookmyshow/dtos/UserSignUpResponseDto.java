package com.ykv17.bookmyshow.dtos;

import com.ykv17.bookmyshow.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpResponseDto {
    private Long userId;
    private ResponseStatus responseStatus;
    private String message;
}
