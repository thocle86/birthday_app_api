package fr.cefim.birthdayapp.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class BirthdayDTO implements Serializable {

    private LocalDate date;

    private String firstname;

    private String lastname;

}
