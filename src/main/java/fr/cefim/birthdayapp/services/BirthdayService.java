package fr.cefim.birthdayapp.services;

import fr.cefim.birthdayapp.dtos.BirthdayDTO;
import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.entities.User;
import fr.cefim.birthdayapp.exceptions.AccessDeniedException;
import fr.cefim.birthdayapp.exceptions.BirthdayNotFoundException;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;

import java.util.List;

public interface BirthdayService {

    List<Birthday> getBirthdaysByUserId(Long userId) throws UserNotFoundException, AccessDeniedException;

    Birthday getBirthdayByUserId(Long userId, Long id) throws UserNotFoundException, BirthdayNotFoundException, AccessDeniedException;

    Birthday createByDTO(Long userId, BirthdayDTO dto) throws UserNotFoundException, AccessDeniedException;

    Birthday updateById(Long userId, Birthday birthday, Long id) throws UserNotFoundException, BirthdayNotFoundException, AccessDeniedException;

    void deleteById(Long userId, Long id) throws UserNotFoundException, BirthdayNotFoundException, AccessDeniedException;

}
