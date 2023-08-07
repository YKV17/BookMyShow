package com.ykv17.bookmyshow.models;

import com.ykv17.bookmyshow.enums.Language;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Movie extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Language> languages;
    private String name;
}
