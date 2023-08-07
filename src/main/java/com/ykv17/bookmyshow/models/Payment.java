package com.ykv17.bookmyshow.models;

import com.ykv17.bookmyshow.enums.PaymentProvider;
import com.ykv17.bookmyshow.enums.PaymentStatus;
import com.ykv17.bookmyshow.enums.PaymentType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Payment extends BaseModel{
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private PaymentProvider paymentProvider;
    private Date time;
    private String refId;
    @Enumerated(EnumType.ORDINAL)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;
}
