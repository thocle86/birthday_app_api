package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.BirthdayDTO;
import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.exceptions.BusinessResourceException;

import java.util.List;
import java.util.Optional;

public interface BirthdayService {

    List<Birthday> getBirthdaysByUserId(Long userId) throws BusinessResourceException;

    Optional<Birthday> getBirthdayByUserId(Long userId, Long id) throws BusinessResourceException;

    Birthday saveBirthdayByDTO(Long userId, BirthdayDTO birthdayDTO) throws BusinessResourceException;

    Birthday updateBirthdayByDTO(Long userId, Long id, BirthdayDTO birthdayDTO) throws BusinessResourceException;

    void deleteById(Long userId, Long id) throws BusinessResourceException;

}
