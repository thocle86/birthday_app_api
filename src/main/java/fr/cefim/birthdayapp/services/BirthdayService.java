package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.entities.Birthday;

import java.util.List;

public interface BirthdayService {

    public List<Birthday> getAllBirthdays();

    public List<Birthday> getBirthdaysByUserId(Long userId);

    public Birthday save(Birthday birthday);

}
