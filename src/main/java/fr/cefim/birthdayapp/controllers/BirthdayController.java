package fr.cefim.birthdayapp.controllers;

import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.repositories.BirthdayRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/birthdays")
public class BirthdayController {

    private final BirthdayRepository mBirthdayRepository;

    public BirthdayController(BirthdayRepository birthdayRepository) {
        mBirthdayRepository = birthdayRepository;
    }

    @GetMapping("/user/{id}")
    public List<Birthday> getBirthdaysByUserId(@PathVariable Long id) {
        return mBirthdayRepository.findBirthdaysByUserId(id);
    }

}
