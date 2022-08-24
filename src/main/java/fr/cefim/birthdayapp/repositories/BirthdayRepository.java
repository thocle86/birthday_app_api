package fr.cefim.birthdayapp.repositories;

import fr.cefim.birthdayapp.entities.Birthday;
import fr.cefim.birthdayapp.exceptions.BirthdayNotFoundException;
import fr.cefim.birthdayapp.exceptions.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirthdayRepository extends JpaRepository<Birthday, Long> {

    List<Birthday> findBirthdaysByUserId(Long id) throws UserNotFoundException;

    Birthday findBirthdayByUserIdAndId(Long userId, Long id) throws UserNotFoundException, BirthdayNotFoundException;

}
